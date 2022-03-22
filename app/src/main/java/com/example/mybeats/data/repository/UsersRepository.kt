package com.example.mybeats.data.repository

import android.content.SharedPreferences
import com.example.mybeats.data.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUser(userName: String, password: String): Flow<User>

    suspend fun insertUser(user: User)

    suspend fun getLoginSharedPreferences(): SharedPreferences
}
