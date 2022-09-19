package com.example.nbaapp.di

import com.example.nbaapp.data.NbaApi
import com.example.nbaapp.data.TeamRepository
import com.example.nbaapp.data.TeamRepositoryImpl
import com.example.nbaapp.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The base URL of the API endpoint.
 */
private const val BASE_URL = "https://www.balldontlie.io/api/v1/"

/**
 * Application module object which provides singletons to various data sources, e.g. API, data repository
 * and the coroutine dispatchers. Can be easily extended to other singletons and/or services which need
 * to be injected in other places of the project.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideNbaApi() : NbaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NbaApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: NbaApi) : TeamRepository = TeamRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDispatcherProvider() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }
}