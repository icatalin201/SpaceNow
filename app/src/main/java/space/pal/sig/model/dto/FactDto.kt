package space.pal.sig.model.dto

import space.pal.sig.model.entity.Fact

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
data class FactDto(
        val content: String,
        val index: String
) {

    fun toFact(): Fact {
        return Fact(content)
    }

}
