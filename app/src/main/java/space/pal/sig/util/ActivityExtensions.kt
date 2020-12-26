package space.pal.sig.util

import space.pal.sig.view.BaseActivity

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
object ActivityExtensions {

    fun BaseActivity.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources
                .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}