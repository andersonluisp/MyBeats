package com.example.mybeats.view.products.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybeats.data.model.Product
import com.example.mybeats.repository.ProductsRepository

class ProductsViewModel(
    private val repository: ProductsRepository
) : ViewModel() {

    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    fun getProducts() {
        val result = repository.getProducts()
        _products.postValue(result)
    }
}