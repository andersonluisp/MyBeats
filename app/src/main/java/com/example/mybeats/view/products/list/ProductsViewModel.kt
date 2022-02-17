package com.example.mybeats.view.products.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybeats.data.model.Product
import com.example.mybeats.data.remote.responses.ResultRemote
import com.example.mybeats.data.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val repository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _productsState = MutableStateFlow<ViewState<List<Product>>>(ViewState.Initial)
    val productsState: StateFlow<ViewState<List<Product>>>
        get() = _productsState

    fun getProducts() {
        _productsState.value = ViewState.Loading
        viewModelScope.launch(dispatcher) {
            try{
                repository.getProducts()
                    .collect {
                        when (it){
                            is ResultRemote.Success -> {
                                _productsState.value = ViewState.Success(it.response)
                            }
                            is ResultRemote.ErrorResponse ->{
                                _productsState.value = ViewState.Error(it.throwable)
                            }
                        }
                    }
            } catch (throwable: Throwable){
                _productsState.value = ViewState.Loading
            }
        }
    }

    sealed class ViewState<out T> {
        object Initial : ViewState<Nothing>()
        object Loading : ViewState<Nothing>()
        data class Success<T>(val data: T) : ViewState<T>()
        data class Error(val throwable: Throwable) : ViewState<Nothing>()
    }
}