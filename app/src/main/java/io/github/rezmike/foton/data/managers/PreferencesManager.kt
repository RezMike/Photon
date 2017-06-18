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
        val PHOTO_CARDS_LAST_UPDATE_KEY = "PHOTO_CARDS_LAST_UPDATE_KEY"
        val ALBUMS_LAST_UPDATE_KEY = "ALBUMS_LAST_UPDATE_KEY"

        val DEFAULT_LAST_UPDATE = "Thu Jan 1 1970 00:00:00 GMT+0000 (UTC)"
    }

    //region ======================== Auth ========================

    fun isUserAuth(): Boolean {
        return getAuthToken() != null
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null)
    }

    fun saveAuthToken(authToken: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_AUTH_TOKEN_KEY, authToken)
        editor.apply()
    }

    fun deleteAuthToken() {
        saveAuthToken(null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(PROFILE_USER_ID_KEY, null)
    }

    fun saveUserId(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_USER_ID_KEY, userId)
        editor.apply()
    }

    //endregion

    //region ======================== Last updates ========================

    fun getLastPhotoCardsUpdate(): String = sharedPreferences.getString(PHOTO_CARDS_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE)

    fun saveLastPhotoCardsUpdate(lastModified: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PHOTO_CARDS_LAST_UPDATE_KEY, lastModified)
        editor.apply()
    }

    fun getLastAlbumsUpdate(): String = sharedPreferences.getString(ALBUMS_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE)

    fun saveLastAlbumsUpdate(lastModified: String) {
        val editor = sharedPreferences.edit()
        editor.putString(ALBUMS_LAST_UPDATE_KEY, lastModified)
        editor.apply()
    }

    //endregion
}
