package space.pal.sig.model

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
enum class PadStatus(
        val status: String
) {
    UNKNOWN("unknown"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    RETIRED("retired"),
    UNDER_CONSTRUCTION("under construction"),
    LOST("lost"),
}