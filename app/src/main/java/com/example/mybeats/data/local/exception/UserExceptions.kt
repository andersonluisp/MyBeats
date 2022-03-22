package com.example.mybeats.data.local.exception

import java.lang.Exception

sealed class UserExceptions : Exception() {
    class WrongPasswordException : UserExceptions() {
        override val message: String
            get() = "Invalid password"
    }
    class UserNotFoundException : UserExceptions() {
        override val message: String
            get() = "User not found"
    }

    class UserAlreadyRegistered : UserExceptions() {
        override val message: String
            get() = "User already registered"
    }
    class UnknownException(override val cause: Throwable) : UserExceptions()
}
