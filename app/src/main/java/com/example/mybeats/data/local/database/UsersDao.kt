package com.example.mybeats.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mybeats.data.local.model.UserEntity

@Dao
interface UsersDao {
    @Query("SELECT * FROM users WHERE userName LIKE :userName")
    suspend fun selectUser(userName: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)
}
