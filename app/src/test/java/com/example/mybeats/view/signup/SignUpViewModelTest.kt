package com.example.mybeats.view.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.data.repository.UsersRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var usersRepository: UsersRepository

    private lateinit var signUpViewModel: SignUpViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        signUpViewModel = SignUpViewModel(usersRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_NAME error`() = runBlocking {
        val name = ""
        val username = "anderson"
        val password = "password"
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(SignUpViewModel.ViewState.FieldError.EMPTY_NAME)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_USERNAME error`() = runBlocking {
        val name = "anderson"
        val username = ""
        val password = "password"
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(SignUpViewModel.ViewState.FieldError.EMPTY_USERNAME)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_PASSWORD error`() = runBlocking {
        val name = "anderson"
        val username = "anderson"
        val password = ""
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(SignUpViewModel.ViewState.FieldError.EMPTY_PASSWORD)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_NAME and EMPTY_USERNAME error`() = runBlocking {
        val name = ""
        val username = ""
        val password = "password"
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(
                    SignUpViewModel.ViewState.FieldError.EMPTY_NAME,
                    SignUpViewModel.ViewState.FieldError.EMPTY_USERNAME
                )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_NAME and EMPTY_PASSWORD error`() = runBlocking {
        val name = ""
        val username = "anderson"
        val password = ""
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(
                    SignUpViewModel.ViewState.FieldError.EMPTY_NAME,
                    SignUpViewModel.ViewState.FieldError.EMPTY_PASSWORD
                )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit just EMPTY_USERNAME and EMPTY_PASSWORD error`() = runBlocking {
        val name = "Anderson"
        val username = ""
        val password = ""
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(
                    SignUpViewModel.ViewState.FieldError.EMPTY_USERNAME,
                    SignUpViewModel.ViewState.FieldError.EMPTY_PASSWORD
                )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit EMPTY USER, USERNAME AND PASSWORD errors`() = runBlocking {
        val name = ""
        val username = ""
        val password = ""
        coEvery { usersRepository.insertUser(any()) } answers {}
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(
                    SignUpViewModel.ViewState.FieldError.EMPTY_PASSWORD,
                    SignUpViewModel.ViewState.FieldError.EMPTY_NAME,
                    SignUpViewModel.ViewState.FieldError.EMPTY_USERNAME
                )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `signUp SHOULD emit USER_ALREADY_REGISTERED error`() = runBlocking {
        val name = "Anderson"
        val username = "anderson"
        val password = "password"
        coEvery { usersRepository.insertUser(any()) } throws UserExceptions.UserAlreadyRegistered()
        signUpViewModel.registerViewState.test {
            signUpViewModel.signUp(name, username, password)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Initial::class.java)
            Truth.assertThat(awaitItem())
                .isInstanceOf(SignUpViewModel.ViewState.Loading::class.java)
            Truth.assertThat((awaitItem() as SignUpViewModel.ViewState.Error).error)
                .containsExactly(
                    SignUpViewModel.ViewState.FieldError.USER_ALREADY_REGISTERED
                )
            cancelAndConsumeRemainingEvents()
        }
    }
}
