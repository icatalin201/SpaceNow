package space.pal.sig.view.launch

import space.pal.sig.model.entity.CrewMember

/**
 * SpaceNow
 * Created by Catalin on 12/27/2020
 **/
interface CrewClickListener {
    fun onClick(crewMember: CrewMember)
}