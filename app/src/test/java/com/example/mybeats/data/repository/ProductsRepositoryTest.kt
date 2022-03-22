package com.example.mybeats.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.remote.extension.toModel
import com.example.mybeats.data.remote.model.ProductsResponse
import com.example.mybeats.data.remote.responses.ResultRemote
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductsRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var productsApi: ProductsApi

    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productsRepository = ProductsRepository(productsApi)
    }

    @Test
    fun `getProducts SHOULD emit ResultRemote Success WHEN receive Response Success`() {
        runBlockingTest {
            // Given
            val productsBody = FakeProductsRepository.getProductsBody()
            val response = Response.success(productsBody)
            coEvery { productsApi.getProducts() } returns response
            // When
            val getProductsResult = productsRepository.getProducts()
            // That
            assertThat(getProductsResult.first())
                .isEqualTo(
                    flowOf(ResultRemote.Success(productsBody.products.map { it.toModel() })).first()
                )
        }
    }

    @Test
    fun `getProducts SHOULD emit MappedError WHEN receive a mapped Error `() {
        runBlockingTest {
            // Given
            val errorCode = 901
            val responseBody = ResponseBody.create(null, "Mocked Mapped Error Test")
            val responseError = Response.error<ProductsResponse>(errorCode, responseBody)
            coEvery { productsApi.getProducts() } returns responseError
            // When
            val getProductsResult = productsRepository.getProducts()
            // That
            assertThat(getProductsResult.first()).isInstanceOf(ResultRemote.ErrorResponse.MappedError::class.java)
        }
    }

    @Test
    fun `getProducts SHOULD emit UnknownError WHEN receive an unmapped Error Code `() {
        runBlockingTest {
            // Given
            val errorCode = 999
            val responseBody = ResponseBody.create(null, "Mocked Unknown Error Test")
            val responseError = Response.error<ProductsResponse>(errorCode, responseBody)
            coEvery { productsApi.getProducts() } returns responseError
            // When
            val getProductsResult = productsRepository.getProducts()
            // That
            assertThat(getProductsResult.first()).isInstanceOf(ResultRemote.ErrorResponse.UnknownError::class.java)
        }
    }
}
