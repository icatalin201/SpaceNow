package space.pal.sig.service

import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import space.pal.sig.R
import space.pal.sig.model.dto.AstronomyPictureOfTheDayDto
import space.pal.sig.model.dto.FactDto
import space.pal.sig.model.entity.NewsSource
import space.pal.sig.repository.*
import space.pal.sig.util.SharedPreferencesUtil
import space.pal.sig.view.splash.SplashActivity
import space.pal.sig.view.splash.SplashViewModel
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
class DataSyncWorker(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    companion object {
        const val DATA_SYNC_WORKER_TAG = "data_sync_worker_tag"
    }

    private val launchRepository: LaunchRepository by inject()
    private val roadsterRepository: RoadsterRepository by inject()
    private val apodRepository: ApodRepository by inject()
    private val rocketRepository: RocketRepository by inject()
    private val launchPadRepository: LaunchPadRepository by inject()
    private val factRepository: FactRepository by inject()
    private val newsRepository: NewsRepository by inject()
    private val crewMemberRepository: CrewMemberRepository by inject()
    private val sharedPreferencesUtil: SharedPreferencesUtil by inject()
    private val gson = Gson()

    private val isFirstTime = sharedPreferencesUtil
            .get(SplashViewModel.IS_FIRST_TIME, true)

    override fun doWork(): Result {
        sendSplashSyncMessage("Initializing...")
        syncAstronomyPicturesOfTheDay()
        syncRoadster()
        syncLaunches()
        sendSplashSyncMessage("Searching for alien life...")
        syncRockets()
        syncLaunchpads()
        syncCrewMembers()
        syncFacts()
        sendSplashSyncMessage("Searching for habitable planets...")
        syncNews()
        return Result.success()
    }

    private fun syncAstronomyPicturesOfTheDay() {
        if (isFirstTime) {
            val json: String = DataSyncUtil.readJsonFromResource(R.raw.apod, applicationContext)
            val apods = gson.fromJson(json, Array<AstronomyPictureOfTheDayDto>::class.java)
            apods.forEach { apod ->
                apodRepository.save(apod.toAstronomyPictureOfTheDay())
            }
        }
        val currentApodDto = apodRepository
                .downloadCurrent()
                .blockingGet()
        apodRepository.save(currentApodDto.toAstronomyPictureOfTheDay())
    }

    private fun syncRoadster() {
        val roadsterDto = roadsterRepository
                .download()
                .blockingGet()
        roadsterRepository.save(roadsterDto.toRoadster())
    }

    private fun syncLaunches() {
        val launches = launchRepository
                .downloadAll()
                .blockingGet()
        launches.forEach { launchDto ->
            launchRepository.save(launchDto.toLaunch())
        }
    }

    private fun syncRockets() {
        val rockets = rocketRepository
                .downloadAll()
                .blockingGet()
        rockets.forEach { rocketDto ->
            rocketRepository.save(rocketDto.toRocket())
        }
    }

    private fun syncLaunchpads() {
        val launchpads = launchPadRepository
                .downloadAll()
                .blockingGet()
        launchpads.forEach { launchpadDto ->
            launchPadRepository.save(launchpadDto.toLaunchpad())
        }
    }

    private fun syncFacts() {
        if (isFirstTime) {
            val json: String = DataSyncUtil.readJsonFromResource(R.raw.facts, applicationContext)
            val facts = gson.fromJson(json, Array<FactDto>::class.java)
            facts.forEach { factDto -> factRepository.save(factDto.toFact()) }
        }
    }

    private fun syncCrewMembers() {
        val crewMembers = crewMemberRepository
                .downloadAll()
                .blockingGet()
        crewMembers.forEach { crewMemberDto ->
            crewMemberRepository.save(crewMemberDto.toCrewMember())
        }
    }

    private fun syncNews() {
        val page = 1
        val hubblePreviewNews = newsRepository
                .downloadHubbleFeed(page)
                .blockingGet()
        hubblePreviewNews.forEach { newsPreviewDto ->
            val newsDto = newsRepository
                    .downloadHubbleNews(newsPreviewDto.id)
                    .blockingGet()
            newsRepository.save(newsDto.toNews(NewsSource.HUBBLE))
        }
        val esaNews = newsRepository
                .downloadEuropeanSpaceAgencyFeed(page)
                .blockingGet()
        esaNews.forEach { newsDto ->
            newsRepository.save(newsDto.toNews(NewsSource.EUROPEAN_SPACE_AGENCY))
        }
        val jwstNews = newsRepository
                .downloadJamesWebbSpaceTelescopeFeed(page)
                .blockingGet()
        jwstNews.forEach { newsDto ->
            newsRepository.save(newsDto.toNews(NewsSource.JAMES_WEBB_SPACE_TELESCOPE))
        }
        val stNews = newsRepository
                .downloadSpaceTelescopeLiveFeed(page)
                .blockingGet()
        stNews.forEach { newsDto ->
            newsRepository.save(newsDto.toNews(NewsSource.SPACE_TELESCOPE_LIVE))
        }
    }

    private fun sendSplashSyncMessage(message: String) {
        if (isFirstTime) {
            val intent = Intent(SplashActivity.SPLASH_SYNC_PROGRESS_ACTION)
            intent.putExtra(SplashActivity.SYNC_MESSAGE, message)
            applicationContext.sendBroadcast(intent)
        }
    }
}