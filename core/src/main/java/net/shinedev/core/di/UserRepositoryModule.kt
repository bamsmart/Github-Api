package net.shinedev.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.shinedev.core.data.repository.UserRepositoryImpl
import net.shinedev.core.domain.repository.UserRepository

@Module(includes = [NetworkModule::class, UserDatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {
    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}