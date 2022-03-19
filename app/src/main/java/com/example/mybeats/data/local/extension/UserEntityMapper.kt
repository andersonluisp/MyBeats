package com.example.mybeats.data.local.extension

import com.example.mybeats.data.local.model.UserEntity
import com.example.mybeats.data.model.User

fun UserEntity.toDomain(): User {
    return User(
        name = this.name,
        userName = this.userName,
        password = this.password
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        name = this.name,
        userName = this.userName,
        password = this.password
    )
}
