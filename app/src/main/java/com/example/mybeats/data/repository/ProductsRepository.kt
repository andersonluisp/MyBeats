package com.example.mybeats.data.repository

import com.example.mybeats.data.model.Product
import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.remote.extension.mapRemoteErrors
import com.example.mybeats.data.remote.extension.toModel
import com.example.mybeats.data.remote.responses.ResultRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsRepository(private val productsApi : ProductsApi) {
    suspend fun getProducts(): Flow<ResultRemote<List<Product>>> {
        return try {
            val response = productsApi.getProducts()
            flowOf(ResultRemote.Success(response.products.map { it.toModel() }))
        } catch (throwable: Throwable){
            flowOf(throwable.mapRemoteErrors())
        }
    }
}