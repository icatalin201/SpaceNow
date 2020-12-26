package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import space.pal.sig.database.dao.RocketDao
import space.pal.sig.model.dto.RocketDto
import space.pal.sig.model.entity.Rocket
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/23/2020
 **/
class RocketRepository(
        private val rocketDao: RocketDao,
        private val spaceXApiService: SpaceXApiService
) {

    fun save(rocket: Rocket) {
        rocketDao.save(rocket)
    }

    fun findAll(): LiveData<MutableList<Rocket>> {
        return rocketDao.findAll()
    }

    fun findById(id: String): LiveData<Rocket> {
        return rocketDao.findById(id)
    }

    fun downloadAll(): Single<MutableList<RocketDto>> {
        return spaceXApiService.getAllRockets()
    }

}