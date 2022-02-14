package com.example.mybeats.di

import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.repository.ProductsRepository
import com.example.mybeats.view.products.list.ProductsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val mainModule = module {
    factory { providesOkHttpClient() }

    single { createWebService<ProductsApi>(
        okHttpClient = get(),
    ) }

    factory { ProductsRepository( productsApi = get()) }

    viewModel {
        ProductsViewModel( repository = get() )
    }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val baseUrl  ="https://mocki.io/v1/"
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}

fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}