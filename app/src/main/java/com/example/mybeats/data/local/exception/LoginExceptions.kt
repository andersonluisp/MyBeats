package com.example.mybeats.data.local.exception

import java.lang.Exception

sealed class LoginExceptions : Exception() {
    class WrongPasswordException : LoginExceptions() {
        override val message: String
            get() = "Invalid password"
    }
    class UserNotFoundException : LoginExceptions() {
        override val message: String
            get() = "User not found"
    }
    class UnknownException(override val cause: Throwable) : LoginExceptions()
}
