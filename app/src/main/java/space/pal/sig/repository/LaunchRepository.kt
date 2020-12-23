package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import space.pal.sig.database.dao.LaunchDao
import space.pal.sig.model.dto.LaunchDto
import space.pal.sig.model.entity.Launch
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class LaunchRepository(
        private val launchDao: LaunchDao,
        private val spaceXApiService: SpaceXApiService
) {

    fun save(launch: Launch) {
        launchDao.save(launch)
    }

    fun findAllLaunches(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findAll()
    }

    fun findLaunch(id: String): LiveData<LaunchWithData> {
        return launchDao.findById(id)
    }

    fun findPastLaunches(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findPastLaunches()
    }

    fun findUpcomingLaunches(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findUpcomingLaunches()
    }

    fun findNextLaunch(): LiveData<LaunchWithData> {
        return launchDao.findNextLaunch()
    }

    fun downloadAllLaunches(): Single<MutableList<LaunchDto>> {
        return spaceXApiService.getAllLaunches()
    }

}