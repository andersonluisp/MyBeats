package com.example.mybeats.view.products.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.mybeats.data.model.Product
import com.example.mybeats.data.remote.responses.ResultRemote
import com.example.mybeats.data.repository.ProductsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var productsViewModel: ProductsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    private val mockedProductsList = listOf(
        Product(
            autonomy = "16 Horas", capture = "Semi ativa",
            compatibility = "Bluetooth 4.1",
            connection = "Bluetooth",
            height = "18,4 cm",
            imageUrl = "imageUrl",
            model = "Fone modelo 02",
            powerSupply = "Bateria",
            price = "20,50",
            rating = 4.6,
            reviews = 86
        ),
        Product(
            autonomy = "16 Horas", capture = "Semi ativa",
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productsViewModel =
            ProductsViewModel(repository = productsRepository, dispatcher = testDispatcher)
    }

    @After
    fun tearDown(){
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getProducts SHOULD emit ViewState Initial, Loading an Success to View WHEN receive ResultRemote Success`(){
        runBlocking {
            coEvery { productsRepository.getProducts() } returns flowOf(
                ResultRemote.Success(
                    mockedProductsList
                )
            )
            productsViewModel.productsState.test {
                productsViewModel.getProducts()
                assertThat(awaitItem()).isInstanceOf(ProductsViewModel.ViewState.Initial::class.java)
                assertThat(awaitItem()).isInstanceOf(ProductsViewModel.ViewState.Loading::class.java)
                assertThat(awaitItem()).isEqualTo(ProductsViewModel.ViewState.Success(mockedProductsList))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `getProducts SHOULD emit ViewState Initial, Loading an Error to View WHEN receive ResultRemote Error`(){
        runBlocking {
            coEvery { productsRepository.getProducts() } returns flowOf(
                ResultRemote.ErrorResponse.Unknown(Exception())
            )
            productsViewModel.productsState.test {
                productsViewModel.getProducts()
                assertThat(awaitItem()).isInstanceOf(ProductsViewModel.ViewState.Initial::class.java)
                assertThat(awaitItem()).isInstanceOf(ProductsViewModel.ViewState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(ProductsViewModel.ViewState.Error::class.java)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}