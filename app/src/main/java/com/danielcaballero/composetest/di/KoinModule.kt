package com.danielcaballero.composetest.di

import android.content.Context
import android.util.Log
import com.danielcaballero.composetest.CountryDataBase
import com.danielcaballero.composetest.common.NetworkConnectivityObserver
import com.danielcaballero.composetest.common.NetworkConnectivityObserverImpl
import com.danielcaballero.composetest.domain.Repository
import com.danielcaballero.composetest.domain.RepositoryImpl
import com.danielcaballero.composetest.model.network.NetworkDataSource
import com.danielcaballero.composetest.model.network.NetworkDataSourceImpl
import com.danielcaballero.composetest.model.remote.LocalDataSource
import com.danielcaballero.composetest.model.remote.LocalDataSourceImpl
import com.danielcaballero.composetest.use_cases.GetCountryUseCase
import com.danielcaballero.composetest.view_model.CountryViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.hilt.android.qualifiers.ApplicationContext
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
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single { /// singleton
        HttpClient(Android) {
            val time = 5000L
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {

                serializer = KotlinxSerializer()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = time
                connectTimeoutMillis = time
                socketTimeoutMillis = time
            }
            defaultRequest {
                if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }

    }



    single<Repository> {
//        RepositoryImpl(get(named("Local")), get())
        RepositoryImpl(get(), get())
    }

    single<NetworkConnectivityObserver> {
        NetworkConnectivityObserverImpl(get<Context>())
    }

//    factory { /// will provide as many dependencies wee need, in this case repoossitory
////        RepositoryImpl(get())
//    }

    single<NetworkDataSource> {
        NetworkDataSourceImpl(get())
    }

//    single<LocalDataSource>(qualifier = named("Local")) { /// this is the way we use qualifier
    single<LocalDataSource> {
        LocalDataSourceImpl(CountryDataBase(get<SqlDriver>()))
    }





    single {
        GetCountryUseCase()
    }


}

val dataBaseModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = CountryDataBase.Schema,
            context = get(),
            name = "country.db"
        )
    }
}

val utilitiesModule = module {
    single {
        CoroutineExceptionHandler { _, throwable ->
            Log.e("CountryViewModel", throwable.toString())
        }
    }
}

val viewModelModule = module {
    viewModelOf(::CountryViewModel)
}
