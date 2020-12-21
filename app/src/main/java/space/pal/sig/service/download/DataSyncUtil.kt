package space.pal.sig.service.download

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
object DataSyncUtil {

    @Throws(IOException::class)
    private fun writeFile(context: Context, filename: String, json: String) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)
        val outputStream = FileOutputStream(file)
        outputStream.write(json.toByteArray())
        outputStream.flush()
        outputStream.close()
    }
}