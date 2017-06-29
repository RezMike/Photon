package io.github.rezmike.foton.utils

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import io.github.rezmike.foton.App
import okhttp3.ResponseBody
import rx.Observable
import java.io.*
import java.text.DateFormat
import java.util.*

fun Context.createFileFromPhoto(): File? {
    val dataTimeInstance = DateFormat.getDateInstance(DateFormat.MEDIUM)
    val timeStamp = dataTimeInstance.format(Date())
    val imageFileName = "IMG_" + timeStamp
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val image: File
    try {
        image = File.createTempFile(imageFileName, ".jpg", storageDir)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    val values = ContentValues()
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    values.put(MediaStore.MediaColumns.DATA, image.absolutePath)
    App.context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    return image
}

fun writeResponseBodyToDisk(body: ResponseBody?): Observable<File> {
    try {
        val dataTimeInstance = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val timeStamp = dataTimeInstance.format(Date())
        val imageFileName = "IMG_" + timeStamp
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val photoFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded = 0

            inputStream = body?.byteStream()
            outputStream = FileOutputStream(photoFile)

            while (true) {
                val read = inputStream!!.read(fileReader)

                if (read == -1) {
                    break
                }

                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read

            }
            outputStream.flush()

            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.MediaColumns.DATA, photoFile.absolutePath)
            App.context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            return Observable.just(photoFile)
        } catch (e: IOException) {
            photoFile.delete()
            return Observable.error(Throwable())
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }

            if (outputStream != null) {
                outputStream.close()
            }
        }
    } catch (e: IOException) {
        return Observable.error(Throwable())
    }

}

