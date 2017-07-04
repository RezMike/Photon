package io.github.rezmike.foton.data.storage.dto

class DialogResult(isOk: Boolean) {

    private val ok = isOk

    fun isOk() = ok

    fun isCancelled() = !ok
}
