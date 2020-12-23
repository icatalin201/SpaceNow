package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import space.pal.sig.database.dao.RoadsterDao
import space.pal.sig.model.dto.RoadsterDto
import space.pal.sig.model.entity.Roadster
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class RoadsterRepository(
        private val roadsterDao: RoadsterDao,
        private val spaceXApiService: SpaceXApiService
) {

    fun save(roadster: Roadster) {
        roadsterDao.save(roadster)
    }

    fun getRoadster(): LiveData<Roadster> {
        return roadsterDao.find()
    }

    fun downloadRoadster(): Single<RoadsterDto> {
        return spaceXApiService.getRoadster()
    }

}