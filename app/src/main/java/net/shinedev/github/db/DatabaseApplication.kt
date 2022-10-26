package com.amartha.dicoding.mysubmission3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.shinedev.github.entity.User
import net.shinedev.github.dao.UserDao

@Database(
    entities = [
        User::class
    ],
    version = 4
)

abstract class DatabaseApplication : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseApplication? = null

        fun getInstance(
            context: Context
        ): DatabaseApplication {
            return INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: Room.databaseBuilder(
                            context.applicationContext,
                            DatabaseApplication::class.java,
                            "GithubApi.db"
                        ).fallbackToDestructiveMigration()
                            .build()
                }
        }
    }

}