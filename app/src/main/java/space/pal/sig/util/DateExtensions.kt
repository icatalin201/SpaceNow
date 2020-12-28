package space.pal.sig.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * SpaceNow
 * Created by Catalin on 12/22/2020
 **/
object DateExtensions {

    fun Date.displayDatetime(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return formatter.format(this)
    }

    fun Date.displayDate(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(this)
    }

    fun Date.formatDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(this)
    }

    fun parseDateString(string: String): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.parse(string)
    }

}
