package com.danielcaballero.composetest.di

import android.app.Application
import android.util.Log
import com.danielcaballero.composetest.CountryDataBase
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.domain.RepositoryImpl
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
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineExceptionHandler


@Module
@InstallIn(ViewModelComponent::class)
interface Module {

//    @Binds
//    fun provideNetworkDataSource(
//        networkDataSourceImpl: NetworkDataSourceImpl
//    ): NetworkDataSource


    companion object {


//        @Provides
//        fun provideExceptionHandler(): CoroutineExceptionHandler =
//            CoroutineExceptionHandler { _, throwable ->
//                Log.e("CountryViewModel", throwable.toString())
//            }

//        @Provides
//        fun provideService(): NetworkCountry =
//            Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(provideOkHttpClient())
//                .build()
//                .create(NetworkCountry::class.java)
//
//
//        @Provides
//        fun provideOkHttpClient(): OkHttpClient =
//            OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                })
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build()

//        @Provides
//        fun provideHttpClient(): HttpClient {
//            return HttpClient(Android) {
//                val time = 3000L
//                install(Logging) {
//                    level = LogLevel.ALL
//                }
//                install(JsonFeature) {
//
//                    serializer = KotlinxSerializer()
//                }
//                install(HttpTimeout) {
//                    requestTimeoutMillis = time
//                    connectTimeoutMillis = time
//                    socketTimeoutMillis = time
//                }
//                defaultRequest {
//                    if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
//                    accept(ContentType.Application.Json)
//                }
//            }
//        }

//        @Provides
//        fun provideSqlDriver(app: Application): SqlDriver = AndroidSqliteDriver(
//            schema = CountryDataBase.Schema,
//            context = app,
//            name = "country.db"
//
//        )
//        @Provides
//        fun provideLocalDataSource(driver: SqlDriver): LocalDataSource =
//            LocalDataSourceImpl(CountryDataBase(driver))

//        @Provides
//        fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver =
//            NetworkConnectivityObserverImpl(context)

//        @Provides
//        fun provideRepository(
//            localDataSource: LocalDataSource,
//            networkDataSource: NetworkDataSource
//        ): Repository =
//            RepositoryImpl(localDataSource, networkDataSource)


    }


}
