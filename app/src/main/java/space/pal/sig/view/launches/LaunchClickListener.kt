package space.pal.sig.view.launches

import space.pal.sig.model.entity.LaunchWithData

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
interface LaunchClickListener {
    fun onClick(launchWithData: LaunchWithData)
}