package com.example.mybeats.view.login

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.data.model.User
import com.example.mybeats.data.repository.UsersRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
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
import java.lang.RuntimeException
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var usersRepository: UsersRepository

    private lateinit var loginViewModel: LoginViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.w(any(), any<Throwable>()) } returns 0
        every { Log.w(any(), any(), any()) } returns 0

        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(usersRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial, Loading an Success to View`() {
        runBlocking {
            val user = User("anderson", "anderson", "password")
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowOf(user)
            loginViewModel.loginState.test(timeout = 2.seconds) {
                loginViewModel.signIn(username, password)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Loading::class.java)
                Truth.assertThat(awaitItem()).isEqualTo(LoginViewModel.ViewState.Success(user))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial, Loading and WRONG_PASSWORD error to View`() {
        runBlocking {
            val exception = UserExceptions.WrongPasswordException()
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            loginViewModel.loginState.test(timeout = 2.seconds) {
                loginViewModel.signIn(username, password)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Loading::class.java)
                Truth.assertThat(awaitItem())
                    .isEqualTo(LoginViewModel.ViewState.Error(LoginViewModel.ViewState.LoginErrors.WRONG_PASSWORD))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial, Loading and USER_NOT_FOUND error to View`() {
        runBlocking {
            val exception = UserExceptions.UserNotFoundException()
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            loginViewModel.loginState.test(timeout = 2.seconds) {
                loginViewModel.signIn(username, password)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Loading::class.java)
                Truth.assertThat(awaitItem())
                    .isEqualTo(LoginViewModel.ViewState.Error(LoginViewModel.ViewState.LoginErrors.USER_NOT_FOUND))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `signIn SHOULD emit ViewState Initial, Loading and UNKNOWN_ERROR error to View`() {
        runBlocking {
            val exception = UserExceptions.UnknownException(RuntimeException("Unit Test"))
            val flowUser = flow<User> {
                throw exception
            }
            val username = "anderson"
            val password = "password"
            coEvery { usersRepository.getUser(any(), any()) } returns flowUser
            loginViewModel.loginState.test(timeout = 2.seconds) {
                loginViewModel.signIn(username, password)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Initial::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(LoginViewModel.ViewState.Loading::class.java)
                Truth.assertThat(awaitItem())
                    .isEqualTo(LoginViewModel.ViewState.Error(LoginViewModel.ViewState.LoginErrors.UNKNOWN_ERROR))
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
