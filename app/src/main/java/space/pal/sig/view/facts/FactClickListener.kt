package space.pal.sig.view.facts

import space.pal.sig.model.dto.FactDto

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
interface FactClickListener {
    fun onClick(fact: FactDto)
}