package io.github.rezmike.foton.data.managers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {

        val PROFILE_AUTH_TOKEN_KEY = "PROFILE_AUTH_TOKEN_KEY"
        val PROFILE_USER_ID_KEY = "PROFILE_USER_ID_KEY"
        val PROFILE_NAME_KEY = "PROFILE_NAME_KEY"
        val PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY"
        val PROFILE_PHONE_KEY = "PROFILE_PHONE_KEY"
        val PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY"

        val DEFAULT_LAST_UPDATE = "Thu Jan 1 1970 00:00:00 GMT+0000 (UTC)"
    }

    fun saveLastProductUpdate(lastModified: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified)
        editor.apply()
    }

    fun getLastProductUpdate(): String = sharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE)
}
