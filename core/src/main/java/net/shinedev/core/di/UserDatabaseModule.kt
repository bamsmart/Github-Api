package net.shinedev.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.shinedev.core.data.source.local.room.UserDao
import net.shinedev.core.data.source.local.room.UserDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserDatabaseModule {

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("shinesdev".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            DB_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.getUserDao()

    companion object {
        const val DB_NAME = "UserGithub.db"
    }
}