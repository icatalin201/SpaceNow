package space.pal.sig.service.download

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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

    override fun doWork(): Result {
        syncAstronomyPicturesOfTheDay()
        syncRoadster()
        syncLaunches()
        syncRockets()
        syncLaunchpads()
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
                .downloadRoadster()
                .blockingGet()
        roadsterRepository.save(roadsterDto.toRoadster())
    }

    private fun syncLaunches() {
        val launches = launchRepository
                .downloadAllLaunches()
                .blockingGet()
        launches.forEach { launchDto ->
            launchRepository.save(launchDto.toLaunch())
        }
    }

    private fun syncRockets() {
        val rockets = rocketRepository
                .downloadAllRockets()
                .blockingGet()
        rockets.forEach { rocketDto ->
            rocketRepository.save(rocketDto.toRocket())
        }
    }

    private fun syncLaunchpads() {
        val launchpads = launchPadRepository
                .downloadAllLaunchpads()
                .blockingGet()
        launchpads.forEach { launchpadDto ->
            launchPadRepository.save(launchpadDto.toLaunchpad())
        }
    }

    companion object {
        const val DATA_SYNC_WORKER_TAG = "data_sync_worker_tag"
    }
}