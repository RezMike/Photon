package io.github.rezmike.foton.utils

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException
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
        return null
    }

    val values = ContentValues()
    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    values.put(MediaStore.MediaColumns.DATA, image.absolutePath)
    this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    return image
}
