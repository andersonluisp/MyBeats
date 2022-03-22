package com.example.mybeats.view.extension

import android.util.Log
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.view.login.LoginViewModel

fun Throwable.toLoginError(): LoginViewModel.ViewState.LoginErrors {
    return when (this) {
        is UserExceptions.UserNotFoundException -> LoginViewModel.ViewState.LoginErrors.USER_NOT_FOUND
        is UserExceptions.WrongPasswordException -> LoginViewModel.ViewState.LoginErrors.WRONG_PASSWORD
        else -> {
            Log.e("***myBeats", "$this : ${this.cause}")
            LoginViewModel.ViewState.LoginErrors.UNKNOWN_ERROR
        }
    }
}
