package com.example.mybeats.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.mybeats.data.local.database.UsersDataBase
import com.example.mybeats.data.local.exception.LoginExceptions
import com.example.mybeats.data.local.extension.toDomain
import com.example.mybeats.data.local.extension.toEntity
import com.example.mybeats.data.local.model.UserEntity
import com.example.mybeats.data.local.sharedpreferences.LoginSharedPreferences
import com.example.mybeats.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl(
    private val usersDataBase: UsersDataBase,
    private val context: Context
) : UsersRepository {

    companion object {
        private const val DELAY_TO_SHOW_LOADING_PROGRESSBAR = 1000L
    }

    override suspend fun insertUser(user: User) {
        usersDataBase.usersDao().insertUser(user.toEntity())
    }

    override suspend fun getLoginSharedPreferences(): SharedPreferences =
        LoginSharedPreferences.getSharedPreferences(context)

    override suspend fun getUser(userName: String, password: String): Flow<User> {
        val flow = flow {
            usersDataBase.usersDao().runCatching {
                selectUser(userName)
            }.onSuccess { user ->
                delay(DELAY_TO_SHOW_LOADING_PROGRESSBAR)
                emit(validateUser(user, password))
                saveLoginCredentials(user, getLoginSharedPreferences())
            }.onFailure { throwable ->
                throw LoginExceptions.UnknownException(throwable)
            }
        }
        return flow
    }

    private fun validateUser(user: UserEntity?, password: String): User {
        user?.let {
            return if (it.password == password)
                user.toDomain()
            else
                throw LoginExceptions.WrongPasswordException()
        } ?: throw LoginExceptions.UserNotFoundException()
    }

    private fun saveLoginCredentials(
        user: UserEntity?,
        sharedPreferences: SharedPreferences
    ) {
        LoginSharedPreferences.saveLoginCredentials(user, sharedPreferences)
    }
}
