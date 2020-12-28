package space.pal.sig.model.dto

/**
SpaceNow
Created by Catalin on 12/28/2020
 **/
data class IssPassDto(
        val message: String,
        val request: Any,
        val response: List<IssPass>
)