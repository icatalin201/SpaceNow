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

    fun findAll(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findAll()
    }

    fun findById(id: String): LiveData<LaunchWithData> {
        return launchDao.findById(id)
    }

    fun findAllFromPast(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findPastLaunches()
    }

    fun findAllFromFuture(): LiveData<MutableList<LaunchWithData>> {
        return launchDao.findUpcomingLaunches()
    }

    fun findLatest(): LiveData<LaunchWithData> {
        return launchDao.findLatestLaunch()
    }

    fun findNext(): LiveData<LaunchWithData> {
        return launchDao.findNextLaunch()
    }

    fun downloadAll(): Single<MutableList<LaunchDto>> {
        return spaceXApiService.getAllLaunches()
    }

}