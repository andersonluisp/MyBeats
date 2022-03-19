package com.example.mybeats.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mybeats.data.local.database.UsersDataBase
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.data.local.extension.toDomain
import com.example.mybeats.data.local.extension.toEntity
import com.example.mybeats.data.local.model.UserEntity
import com.example.mybeats.data.local.sharedpreferences.LoginSharedPreferences
import com.example.mybeats.data.model.User
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class UsersRepositoryImplTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private var usersDatabase = mockk<UsersDataBase>()

    @MockK(relaxed = true)
    private var context = mockk<Context>()

    private lateinit var usersRepository: UsersRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        usersRepository = UsersRepositoryImpl(usersDatabase, context)
        mockkObject(LoginSharedPreferences)
    }

    @Test
    fun `insertUser SHOULD insert userEntity to list WHEN user is not registered`() = runBlockingTest {
        val mockUsersRegistered = listOf(UserEntity("Anderson", "anderson", "password"))
        val result = mutableListOf<UserEntity>()
        val userNotRegistered = User("Anderson", "UserNotRegistered", "password")
        coEvery { usersDatabase.usersDao().selectUser(any()) } answers {
            mockUsersRegistered.firstOrNull { it.userName == userNotRegistered.userName }
        }
        coEvery { usersDatabase.usersDao().insertUser(any()) } answers {
            result.add(userNotRegistered.toEntity())
        }
        usersRepository.insertUser(userNotRegistered)
        Truth.assertThat(result).contains(userNotRegistered.toEntity())
    }

    @Test
    fun `insertUser SHOULD throw UserAlreadyRegistered Exception WHEN already registered`() =
        runBlockingTest {
            val mockUsersRegistered = listOf(UserEntity("Anderson", "anderson", "password"))
            val result = mutableListOf<UserEntity>()
            val userAleradyRegistered = User("Anderson", "anderson", "password")
            coEvery { usersDatabase.usersDao().selectUser(any()) } answers {
                mockUsersRegistered.firstOrNull { it.userName == userAleradyRegistered.userName }
            }
            coEvery { usersDatabase.usersDao().insertUser(any()) } answers {
                result.add(userAleradyRegistered.toEntity())
            }
            try {
                usersRepository.insertUser(userAleradyRegistered)
            } catch (exception: UserExceptions.UserAlreadyRegistered) {
                Truth.assertThat(exception).isInstanceOf(UserExceptions.UserAlreadyRegistered::class.java)
            }
        }

    @Test
    fun `getLoginSharedPreferences SHOULD be called`() = runBlockingTest {
        val mockSharedPreferences = mockk<SharedPreferences>()
        coEvery { LoginSharedPreferences.getSharedPreferences(any()) } answers { mockSharedPreferences }
        usersRepository.getLoginSharedPreferences()
        coVerify { LoginSharedPreferences.getSharedPreferences(any()) }
    }

    @Test
    fun `getUser SHOULD returns User`() = runBlockingTest {
        val user = UserEntity("anderson", "anderson", "password")
        val username = "anderson"
        val password = "password"
        coEvery { usersDatabase.usersDao().selectUser(any()) } returns user
        val result = usersRepository.getUser(username, password)
        Truth.assertThat(result.first()).isEqualTo(user.toDomain())
    }

    @Test
    fun `getUser SHOULD throw WrongPasswordException`() = runBlockingTest {
        try {
            val user = UserEntity("anderson", "anderson", "password")
            val username = "anderson"
            val password = "wrongPassword"
            coEvery { usersDatabase.usersDao().selectUser(any()) } returns user
            usersRepository.getUser(username, password).first()
        } catch (exception: Exception) {
            Truth.assertThat(exception).isInstanceOf(UserExceptions.WrongPasswordException::class.java)
        }
    }

    @Test
    fun `getUser SHOULD throw UserNotFoundException`() = runBlockingTest {
        try {
            val user = UserEntity("anderson", "anderson", "password")
            val username = "wrongUsername"
            val password = "password"
            coEvery { usersDatabase.usersDao().selectUser(any()) } returns user
            usersRepository.getUser(username, password).first()
        } catch (exception: Exception) {
            Truth.assertThat(exception).isInstanceOf(UserExceptions.UserNotFoundException::class.java)
        }
    }

    @Test
    fun `getUser SHOULD throw UnknownException`() = runBlockingTest {
        try {
            val username = "anderson"
            val password = "password"
            coEvery { usersDatabase.usersDao().selectUser(any()) } throws Exception("Unit test exception")
            usersRepository.getUser(username, password).first()
        } catch (exception: Exception) {
            Truth.assertThat(exception).isInstanceOf(UserExceptions.UnknownException::class.java)
        }
    }
}
