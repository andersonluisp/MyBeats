package com.example.mybeats.di

import com.example.mybeats.repository.ProductsRepository
import com.example.mybeats.view.products.list.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        ProductsViewModel(
            repository = ProductsRepository()
        )
    }
}