package com.example.mybeats.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mybeats.data.remote.api.ProductsApi
import com.example.mybeats.data.remote.extension.toModel
import com.example.mybeats.data.remote.model.ProductBody
import com.example.mybeats.data.remote.model.ProductsBody
import com.example.mybeats.data.remote.responses.ResultRemote
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProductsRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var productsApi: ProductsApi

    private lateinit var productsRepository: ProductsRepository

    private val mockedProductBody = ProductsBody(
        listOf(
            ProductBody(
                autonomy = "16 Horas",
                capture = "Semi ativa",
                compatibility = "Bluetooth 4.1",
                connection = "Bluetooth",
                height = "18,4 cm",
                imageUrl = "imageUrl",
                model = "Fone modelo 02",
                powerSupply = "Bateria",
                price = "20,50",
                rating = 4.6,
                reviews = 86
            )
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productsRepository = ProductsRepository(productsApi)
    }

    @Test
    fun `getProducts SHOULD emit ResultRemote Success WHEN receive ProductBody`() {
        runBlockingTest {
            //Given
            coEvery { productsApi.getProducts() } returns mockedProductBody
            //When
            val getProductsResult = productsRepository.getProducts()
            //That
            assertThat(getProductsResult.first()).isEqualTo(flowOf(ResultRemote.Success(mockedProductBody.products.map { it.toModel() })).first())
        }
    }

    @Test
    fun `getProducts SHOULD emit ResultRemote ErrorResponse WHEN receive an Exception`(){
        runBlockingTest {
            //Given
            val throwable = Throwable()
            coEvery { productsApi.getProducts() } throws throwable
            //When
            val getProductsResult = productsRepository.getProducts()
            //That
            assertThat(getProductsResult.first()).isInstanceOf(ResultRemote.ErrorResponse::class.java)
        }
    }
}