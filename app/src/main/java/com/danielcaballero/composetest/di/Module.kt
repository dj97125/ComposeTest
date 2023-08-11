package com.danielcaballero.composetest.di

import android.util.Log
import com.danielcaballero.composetest.common.BASE_URL
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.domain.RepositoryImpl
import com.danielcaballero.composetest.model.network.LocalDataSource
import com.danielcaballero.composetest.model.network.LocalDataSourceImpl
import com.danielcaballero.composetest.model.network.NetworkCountry
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(ViewModelComponent::class)
interface Module {

    @Binds
    fun provideLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    fun provideRepositoory(
        repositoryImpl: RepositoryImpl
    ): Repository

    companion object {


        @Provides
        fun provideExceptionHandler(): CoroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Log.e("CountrryViewModel", throwable.toString())
            }

        @Provides
        fun provideService(): NetworkCountry =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
                .create(NetworkCountry::class.java)


        @Provides
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

    }
}