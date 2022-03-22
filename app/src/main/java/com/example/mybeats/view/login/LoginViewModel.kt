package com.example.mybeats.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybeats.data.model.User
import com.example.mybeats.data.repository.UsersRepository
import com.example.mybeats.view.extension.toLoginError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _userLoginState = MutableStateFlow<ViewState<User>>(ViewState.Initial)
    val loginState: StateFlow<ViewState<User>>
        get() = _userLoginState

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(dispatcher) {
            usersRepository.getUser(userName, password)
                .onStart {
                    _userLoginState.value = ViewState.Loading
                }
                .catch { e ->
                    _userLoginState.value = ViewState.Error(e.toLoginError())
                }
                .collect {
                    _userLoginState.value = ViewState.Success(it)
                }
        }
    }

    sealed class ViewState<out T> {
        object Initial : ViewState<Nothing>()
        object Loading : ViewState<Nothing>()
        data class Success<T>(val data: T) : ViewState<T>()
        data class Error(val error: LoginErrors) : ViewState<Nothing>()

        enum class LoginErrors {
            USER_NOT_FOUND,
            WRONG_PASSWORD,
            UNKNOWN_ERROR
        }
    }
}
