package space.pal.sig.service.download

import android.content.Context
import android.os.Environment
import java.io.*
import java.nio.charset.StandardCharsets

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 */
object DataSyncUtil {

    @Throws(IOException::class)
    fun writeFile(context: Context, filename: String, json: String) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)
        val outputStream = FileOutputStream(file)
        outputStream.write(json.toByteArray())
        outputStream.flush()
        outputStream.close()
    }

    @Throws(IOException::class)
    fun readJsonFromResource(resource: Int, context: Context): String {
        val inputStream: InputStream = context.resources
                .openRawResource(resource)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        inputStream.use {
            val reader: Reader = BufferedReader(
                    InputStreamReader(inputStream, StandardCharsets.UTF_8))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }
}