package com.example.mybeats.data.repository

import com.example.mybeats.data.model.Product
import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.remote.extension.toErrorResponse
import com.example.mybeats.data.remote.extension.toModel
import com.example.mybeats.data.remote.responses.ResultRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsRepository(private val productsApi: ProductsApi) {
    suspend fun getProducts(): Flow<ResultRemote<List<Product>>> {
        val response = productsApi.getProducts()
        return flowOf(
            if (response.isSuccessful && response.body() != null) {
                ResultRemote.Success(response.body()!!.products.map { it.toModel() })
            } else {
                response.toErrorResponse()
            }
        )
    }
}
