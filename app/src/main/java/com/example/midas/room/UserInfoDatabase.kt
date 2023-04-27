package com.example.midas.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserInfoDatabase? = null

        fun getDatabase(context: Context): UserInfoDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserInfoDatabase::class.java,
                    "UserInfoDatabase"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}