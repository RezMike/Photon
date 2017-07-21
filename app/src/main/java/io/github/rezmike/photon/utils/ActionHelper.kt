package io.github.rezmike.photon.utils

import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import java.io.File

fun getGalleryIntent(): Intent {
    val intent = Intent()
    if (Build.VERSION.SDK_INT < 19) {
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
    } else {
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
    }
    return intent
}

fun getCameraIntent(file: File): Intent {
    val cameraIntent = Intent()
    cameraIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(file))
    return cameraIntent
}
