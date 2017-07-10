package io.github.rezmike.foton.ui.others

import android.graphics.drawable.Drawable
import android.widget.EditText
import io.github.rezmike.foton.R

fun EditText.changeError(isError: Boolean, messageError: String? = null) {
    val drawable: Drawable
    val color: Int
    if (isError) {
        drawable = context.resources.getDrawable(R.drawable.borders_edit_text_error)
        color = context.resources.getColor(R.color.dialog_borders_et_error)
    } else {
        drawable = context.resources.getDrawable(R.drawable.borders_edit_text)
        color = context.resources.getColor(R.color.black)
    }
    background = drawable
    error = messageError
    setTextColor(color)
}
