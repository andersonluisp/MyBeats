package com.example.mybeats.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybeats.data.local.model.UserEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    version = 1,
    entities = [UserEntity::class]
)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun usersDao(): UsersDao

    companion object {
        fun createDataBase(context: Context): UsersDataBase {
            val factory = SupportFactory(SQLiteDatabase.getBytes("mybeats".toCharArray()))
            return Room
                .databaseBuilder(context, UsersDataBase::class.java, "users_database")
                .openHelperFactory(factory)
                .build()
        }
    }
}
