package space.pal.sig.model.dto

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
enum class CoreStatus(
        val value: String
) {
    UNKNOWN("unknown"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    RETIRED("retired"),
    EXPENDED("expended"),
    LOST("lost"),
}