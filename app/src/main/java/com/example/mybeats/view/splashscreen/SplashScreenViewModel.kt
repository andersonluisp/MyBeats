package com.example.mybeats.view.splashscreen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybeats.data.model.User
import com.example.mybeats.data.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenViewModel(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _userStateFlow = MutableStateFlow<ViewState<User>>(ViewState.Initial)
    val userStateFlow: StateFlow<ViewState<User>>
        get() = _userStateFlow

    fun attemptLogin(username: String?, password: String?) {
        viewModelScope.launch(dispatcher) {
            if (username != null && password != null) {
                usersRepository.getUser(username, password)
                    .catch {
                        _userStateFlow.value = ViewState.Navigate(
                            data = null,
                            destination = ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
                        )
                    }
                    .collect {
                        _userStateFlow.value = ViewState.Navigate(
                            data = it,
                            destination = ViewState.SplashScreenNavigation.NAVIGATE_TO_PRODUCTS_ACTIVITY
                        )
                    }
            } else _userStateFlow.value = ViewState.Navigate(
                data = null,
                destination = ViewState.SplashScreenNavigation.NAVIGATE_TO_LOGIN_ACTIVITY
            )
        }
    }

    sealed class ViewState<out T> {
        object Initial : ViewState<Nothing>()
        data class Navigate<T>(val data: T?, val destination: SplashScreenNavigation) :
            ViewState<T>()

        enum class SplashScreenNavigation {
            NAVIGATE_TO_PRODUCTS_ACTIVITY,
            NAVIGATE_TO_LOGIN_ACTIVITY
        }
    }
}
