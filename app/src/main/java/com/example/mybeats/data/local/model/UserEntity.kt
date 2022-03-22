package com.example.mybeats.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["userName"], unique = true)])
data class UserEntity(
    val name: String,
    @PrimaryKey
    val userName: String,
    val password: String
)
