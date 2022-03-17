package com.example.mybeats.di

import com.example.mybeats.view.login.LoginViewModel
import com.example.mybeats.view.products.list.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        ProductsViewModel(repository = get())
    }
    viewModel {
        LoginViewModel(usersRepository = get())
    }
}
