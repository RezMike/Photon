package io.github.rezmike.photon.ui.others

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addAfterTextChangedListener(onAfterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            onAfterTextChanged(s.toString())
        }
    })
}

abstract class CustomTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}