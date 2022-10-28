package net.shinedev.github.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.shinedev.core.domain.usecase.UserUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerDynamicFeatureDependencies {
    fun provideUserUseCase(): UserUseCase
}