package space.pal.sig.model.dto

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
enum class CrewMemberStatus(
        val value: String
) {
    UNKNOWN("unknown"),
    ACTIVE("active"),
    RETIRED("retired"),
    INACTIVE("inactive")
}