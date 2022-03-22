package com.example.mybeats.di

import android.content.Context
import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.remote.interceptor.LoggingInterceptor
import com.example.mybeats.data.repository.ProductsRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECT_TIMEOUT = 30L
const val READ_TIMEOUT = 30L
const val WRITE_TIMEOUT = 30L

val remoteDataModule = module {
    factory { providesOkHttpClient(androidContext()) }

    single {
        createWebService<ProductsApi>(
            okHttpClient = get(),
        )
    }

    factory { ProductsRepository(productsApi = get()) }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val baseUrl = "https://mocki.io/v1/"
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}

fun providesOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor(context))
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()
}
