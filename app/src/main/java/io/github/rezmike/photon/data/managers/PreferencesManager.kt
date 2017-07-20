package io.github.rezmike.photon.data.managers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.github.rezmike.photon.data.network.res.UserRes

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {

        val PROFILE_AUTH_TOKEN_KEY = "PROFILE_AUTH_TOKEN_KEY"
        val PROFILE_USER_ID_KEY = "PROFILE_USER_ID_KEY"
        val PROFILE_NAME_KEY = "PROFILE_NAME_KEY"
        val PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY"
        val PROFILE_LOGIN_KEY = "PROFILE_LOGIN_KEY"

        val PHOTO_CARDS_LAST_UPDATE_KEY = "PHOTO_CARDS_LAST_UPDATE_KEY"
        val ALBUMS_LAST_UPDATE_KEY = "ALBUMS_LAST_UPDATE_KEY"
        val USER_LAST_UPDATE_KEY = "USER_LAST_UPDATE_KEY"

        val DEFAULT_LAST_UPDATE = "Thu Jan 1 1970 00:00:00 GMT+0000 (UTC)"
    }

    //region ======================== Auth ========================

    fun isUserAuth(): Boolean {
        return getAuthToken() != null
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(PROFILE_USER_ID_KEY, null)
    }

    fun getUserLogin(): String? {
        return sharedPreferences.getString(PROFILE_LOGIN_KEY, null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(PROFILE_NAME_KEY, null)
    }

    fun deleteUserData() {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_USER_ID_KEY, null)
        editor.putString(PROFILE_NAME_KEY, null)
        editor.putString(PROFILE_LOGIN_KEY, null)
        editor.putString(PROFILE_AVATAR_KEY, null)
        editor.putString(PROFILE_AUTH_TOKEN_KEY, null)
        editor.apply()
    }

    fun saveUserData(userRes: UserRes) {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_USER_ID_KEY, userRes.id)
        editor.putString(PROFILE_NAME_KEY, userRes.name)
        editor.putString(PROFILE_LOGIN_KEY, userRes.login)
        editor.putString(PROFILE_AVATAR_KEY, userRes.avatar)
        editor.putString(PROFILE_AUTH_TOKEN_KEY, userRes.token)
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

    fun saveUserLastUpdate(userId: String, lastUpdate: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_LAST_UPDATE_KEY + userId, lastUpdate)
        editor.apply()
    }

    fun getLastUserUpdate(userId: String) = sharedPreferences.getString(USER_LAST_UPDATE_KEY + userId, DEFAULT_LAST_UPDATE)

    //endregion
}
