package space.pal.sig.repository

import io.reactivex.Single
import space.pal.sig.database.dao.CrewMemberDao
import space.pal.sig.model.dto.CrewMemberDto
import space.pal.sig.model.entity.CrewMember
import space.pal.sig.network.SpaceXApiService

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
class CrewMemberRepository(
        private val crewMemberDao: CrewMemberDao,
        private val spaceXApiService: SpaceXApiService
) {

    fun save(crewMember: CrewMember) {
        crewMemberDao.save(crewMember)
    }

    fun downloadAll(): Single<MutableList<CrewMemberDto>> {
        return spaceXApiService.getAllCrewMembers()
    }

}