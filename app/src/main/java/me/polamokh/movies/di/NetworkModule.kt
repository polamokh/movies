package me.polamokh.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.polamokh.movies.network.TMDBService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideTMDBService(): TMDBService {
        return TMDBService.create()
    }
}