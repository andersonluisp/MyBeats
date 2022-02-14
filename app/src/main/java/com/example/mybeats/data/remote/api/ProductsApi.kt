package com.example.mybeats.data.remote.api

import com.example.mybeats.data.remote.model.ProductsBody
import retrofit2.http.GET

interface ProductsApi {
    @GET("eab81402-d76f-4433-96bf-39541646744e")
    suspend fun getProducts(): ProductsBody
}