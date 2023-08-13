package com.danielcaballero.composetest.di

import android.app.Application
import android.content.Context
import android.util.Log
import com.danielcaballero.composetest.CountryDataBase
import com.danielcaballero.composetest.common.BASE_URL
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.NetworkConnectivityObserverImpl
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.domain.RepositoryImpl
import com.danielcaballero.composetest.model.network.NetworkCountry
import com.danielcaballero.composetest.model.network.NetworkDataSource
import com.danielcaballero.composetest.model.network.NetworkDataSourceImpl
import com.danielcaballero.composetest.model.remote.LocalDataSource
import com.danielcaballero.composetest.model.remote.LocalDataSourceImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideNetworkDataSource(
        networkDataSourceImpl: NetworkDataSourceImpl
    ): NetworkDataSource


    companion object {


        @Provides
        fun provideExceptionHandler(): CoroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Log.e("CountryViewModel", throwable.toString())
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

        @Provides
        fun provideSqlDriver(app: Application): SqlDriver = AndroidSqliteDriver(
            schema = CountryDataBase.Schema,
            context = app,
            name = "country.db"

        )


        @Provides
        fun provideLocalDataSource(driver: SqlDriver): LocalDataSource =
            LocalDataSourceImpl(CountryDataBase(driver))

        @Provides
        fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver =
            NetworkConnectivityObserverImpl(context)

        @Provides
        fun provideRepository(
            networkConnectivityObserver: NetworkConnectivityObserver,
            localDataSource: LocalDataSource,
            networkDataSource: NetworkDataSource
        ): Repository =
            RepositoryImpl(networkConnectivityObserver, localDataSource, networkDataSource)


    }


}
