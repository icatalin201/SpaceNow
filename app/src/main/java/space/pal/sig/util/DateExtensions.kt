package space.pal.sig.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/

fun Date.displayDatetime(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return formatter.format(this)
}

fun Date.displayDate(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(this)
}