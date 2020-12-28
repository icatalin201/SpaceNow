package space.pal.sig.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import space.pal.sig.R
import space.pal.sig.model.dto.FactDto
import space.pal.sig.model.entity.NewsSource
import space.pal.sig.repository.*

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
class DataSyncManager(
        context: Context,
        workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    private val launchRepository: LaunchRepository by inject()
    private val roadsterRepository: RoadsterRepository by inject()
    private val apodRepository: ApodRepository by inject()
    private val rocketRepository: RocketRepository by inject()
    private val launchPadRepository: LaunchPadRepository by inject()
    private val factRepository: FactRepository by inject()
    private val newsRepository: NewsRepository by inject()
    private val crewMemberRepository: CrewMemberRepository by inject()
    private val gson = Gson()

    override fun doWork(): Result {
        syncAstronomyPicturesOfTheDay()
        syncRoadster()
        syncLaunches()
        syncRockets()
        syncLaunchpads()
        syncCrewMembers()
        syncFacts()
        syncNews()
        return Result.success()
    }

    private fun syncAstronomyPicturesOfTheDay() {
        val apod = apodRepository
                .downloadCurrent()
                .blockingGet()
        apodRepository.save(apod.toAstronomyPictureOfTheDay())
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
        val factsJson: String = DataSyncUtil.readJsonFromResource(R.raw.facts, applicationContext)
        val facts = gson.fromJson(factsJson, Array<FactDto>::class.java)
        facts.forEach { factDto -> factRepository.save(factDto.toFact()) }
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

    companion object {
        const val DATA_SYNC_WORKER_TAG = "data_sync_worker_tag"
    }
}