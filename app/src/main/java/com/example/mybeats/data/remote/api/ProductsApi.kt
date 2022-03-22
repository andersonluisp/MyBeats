package com.example.mybeats.data.remote.api

import com.example.mybeats.data.remote.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("eab81402-d76f-4433-96bf-39541646744e")
    suspend fun getProducts(): Response<ProductsResponse>
}
