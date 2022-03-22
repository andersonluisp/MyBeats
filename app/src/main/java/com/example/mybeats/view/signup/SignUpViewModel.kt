package com.example.mybeats.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybeats.data.local.exception.UserExceptions
import com.example.mybeats.data.model.User
import com.example.mybeats.data.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val usersRepository: UsersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _registerViewState = MutableStateFlow<ViewState>(ViewState.Initial)
    val registerViewState: StateFlow<ViewState>
        get() = _registerViewState

    @Suppress("SwallowedException")
    fun signUp(name: String, username: String, password: String) {
        _registerViewState.value = ViewState.Loading
        if (fieldsValidation(name, username, password)) {
            viewModelScope.launch(dispatcher) {
                try {
                    val user = User(name, username, password)
                    usersRepository.insertUser(user)
                    _registerViewState.value = ViewState.Success
                } catch (e: UserExceptions.UserAlreadyRegistered) {
                    val error = listOf(ViewState.FieldError.USER_ALREADY_REGISTERED)
                    _registerViewState.value = ViewState.Error(error)
                }
            }
        }
    }

    private fun fieldsValidation(name: String, username: String, password: String): Boolean {
        val errorFieldList = mutableListOf<ViewState.FieldError>()
        if (name.isEmpty()) errorFieldList.add(ViewState.FieldError.EMPTY_NAME)
        if (username.isEmpty()) errorFieldList.add(ViewState.FieldError.EMPTY_USERNAME)
        if (password.isEmpty()) errorFieldList.add(ViewState.FieldError.EMPTY_PASSWORD)
        if (errorFieldList.isNotEmpty()) _registerViewState.value = ViewState.Error(errorFieldList)
        return errorFieldList.isEmpty()
    }

    sealed class ViewState {
        object Initial : ViewState()
        object Success : ViewState()
        object Loading : ViewState()
        data class Error(val error: List<FieldError>) : ViewState()

        enum class FieldError {
            EMPTY_USERNAME,
            EMPTY_PASSWORD,
            EMPTY_NAME,
            USER_ALREADY_REGISTERED
        }
    }
}
