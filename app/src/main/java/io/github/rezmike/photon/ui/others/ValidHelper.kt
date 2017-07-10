package io.github.rezmike.photon.ui.others

import android.text.TextUtils
import java.util.regex.Pattern

val PATTERN_LOGIN: Pattern = Pattern.compile("^[A-Za-z0-9_]{3,}$")
val PATTERN_EMAIL: Pattern = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&" +
        "'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\" +
        "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z" +
        "0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0" +
        "-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f" +
        "\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
val PATTERN_NAME: Pattern = Pattern.compile("^[\\s\\S]{3,}$")
val PATTERN_PASSWORD: Pattern = Pattern.compile("^[A-Za-z0-9]{8,}")

fun String.isLoginValid(): Boolean {
    return TextUtils.isEmpty(this) || PATTERN_LOGIN.matcher(this).matches()
}

fun String.isEmailValid(): Boolean {
    return TextUtils.isEmpty(this) || PATTERN_EMAIL.matcher(this).matches()
}

fun String.isNameValid(): Boolean {
    return TextUtils.isEmpty(this) || PATTERN_NAME.matcher(this).matches()
}

fun String.isPasswordValid(): Boolean {
    return TextUtils.isEmpty(this) || PATTERN_PASSWORD.matcher(this).matches()
}
