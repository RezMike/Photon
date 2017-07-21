package io.github.rezmike.photon.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.commonsware.cwac.provider.StreamProvider
import io.github.rezmike.photon.App
import io.github.rezmike.photon.BuildConfig
import okhttp3.ResponseBody
import rx.Observable
import java.io.*
import java.text.DateFormat
import java.util.*

fun createFileForPhoto(): File? {
    val dataTimeInstance = DateFormat.getDateInstance(DateFormat.MEDIUM)
    val timeStamp = dataTimeInstance.format(Date())
    val imageFileName = "IMG_" + timeStamp
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val imageFile: File

    try {
        imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    val values = ContentValues()
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    values.put(MediaStore.MediaColumns.DATA, imageFile.absolutePath)
    App.context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    return imageFile
}

fun writePhotoToDisk(body: ResponseBody): Observable<File> {
    val timeStamp = DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date())
    val imageFileName = "IMG_" + timeStamp
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val photoFile: File?
    try {
        photoFile = File.createTempFile(imageFileName, ".jpg", storageDir)
    } catch (e: IOException) {
        return Observable.error(e)
    }

    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    try {
        val fileReader = ByteArray(4096)
        var fileSizeDownloaded = 0

        inputStream = body.byteStream()
        outputStream = FileOutputStream(photoFile)

        while (true) {
            val read = inputStream.read(fileReader)
            if (read == -1) {
                break
            }
            outputStream.write(fileReader, 0, read)
            fileSizeDownloaded += read
        }
        outputStream.flush()

        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = Uri.fromFile(photoFile)
        App.context.sendBroadcast(intent)

        return Observable.just(photoFile)
    } catch (e: IOException) {
        photoFile.delete()
        return Observable.error(e)
    } finally {
        inputStream?.close()
        outputStream?.close()
    }
}

fun getFileFromUri(uri: Uri, context: Context): File {
    var filePath = ""
    val wholeID = DocumentsContract.getDocumentId(uri)
    val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
    val column = arrayOf(MediaStore.Images.Media.DATA)
    val sel = MediaStore.Images.Media._ID + "=?"
    val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null)
    val columnIndex = cursor.getColumnIndex(column[0])
    if (cursor.moveToFirst()) {
        filePath = cursor.getString(columnIndex)
    }
    cursor.close()
    return File(filePath)
}

fun getUriFromFile(file: File): Uri {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return StreamProvider.getUriForFile(BuildConfig.APPLICATION_ID + ".fileprovider", file)
    } else {
        return Uri.fromFile(file)
    }
}
