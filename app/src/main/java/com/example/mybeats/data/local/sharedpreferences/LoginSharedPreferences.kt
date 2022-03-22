package com.example.mybeats.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.example.mybeats.data.local.model.UserEntity
import com.example.mybeats.data.local.utils.AndroidKeyGenerator

object LoginSharedPreferences {

    private const val USER_LOGIN_SHARED_PREFERENCES = "users_login"

    fun getSharedPreferences(context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            USER_LOGIN_SHARED_PREFERENCES,
            AndroidKeyGenerator.masterKeyProvide(context), // masterKey created above
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveLoginCredentials(user: UserEntity?, sharedPreferences: SharedPreferences?) {
        val sharedPreferencesEditor = sharedPreferences?.edit()
        sharedPreferencesEditor
            ?.putString("username", user?.userName)
            ?.putString("password", user?.password)
            ?.apply()
    }

    fun getSavedCredentials(context: Context): HashMap<String, String?> {
        val sharedPreferences = getSharedPreferences(context)
        val username: String? = sharedPreferences.getString("username", null)
        val password: String? = sharedPreferences.getString("password", null)
        return HashMap<String, String?>().apply {
            put("username", username)
            put("password", password)
        }
    }

    fun deleteSavedCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit()
            .remove("username")
            .remove("password")
            .apply()
    }
}
