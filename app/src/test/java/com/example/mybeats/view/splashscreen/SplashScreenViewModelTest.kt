package com.example.mybeats.view.splashscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.data.model.User
import com.example.mybeats.data.repository.UsersRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class SplashScreenViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var usersRepository: UsersRepository

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        splashScreenViewModel = SplashScreenViewModel(usersRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_PRODUCTS to View`() {
        runBlocking {
            val user = User("anderson", "anderson", "password")
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowOf(user)
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        user,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_PRODUCTS_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN username is null`() {
        runBlocking {
            val user = User("anderson", "anderson", "password")
            val username: String? = null
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowOf(user)
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN password is null`() {
        runBlocking {
            val user = User("anderson", "anderson", "password")
            val username = "anderson"
            val password: String? = null
            coEvery { usersRepository.getUser(any(), any()) } returns flowOf(user)
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN username and password is null`() {
        runBlocking {
            val user = User("anderson", "anderson", "password")
            val username: String? = null
            val password: String? = null
            coEvery { usersRepository.getUser(any(), any()) } returns flowOf(user)
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN throw WrongPasswordException`() {
        runBlocking {
            val exception = UserExceptions.WrongPasswordException()
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN throw UserNotFoundException`() {
        runBlocking {
            val exception = UserExceptions.UserNotFoundException()
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial and NAVIGATE_TO_LOGIN WHEN throw UnknownException`() {
        runBlocking {
            val exception = UserExceptions.UnknownException(RuntimeException("Unit test"))
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            splashScreenViewModel.userStateFlow.test(timeout = 2.seconds) {
                splashScreenViewModel.attemptLogin(username, password)
                Truth.assertThat(awaitItem())
                    .isInstanceOf(SplashScreenViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(
                    SplashScreenViewModel.ViewState.Navigate(
                        null,
                        SplashScreenViewModel.ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                    )
                )
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
