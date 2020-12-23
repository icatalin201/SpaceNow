package space.pal.sig.repository

import io.reactivex.Single
import space.pal.sig.database.dao.LaunchPadDao
import space.pal.sig.model.dto.LaunchPadDto
import space.pal.sig.model.entity.LaunchPad
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class LaunchPadRepository(
        private val launchPadDao: LaunchPadDao,
        private val spaceXApiService: SpaceXApiService
) {

    fun save(launchPad: LaunchPad) {
        launchPadDao.save(launchPad)
    }

    fun downloadAllLaunchpads(): Single<MutableList<LaunchPadDto>> {
        return spaceXApiService.getAllLaunchpads()
    }

}