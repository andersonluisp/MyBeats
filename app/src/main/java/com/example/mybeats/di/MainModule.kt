package com.example.mybeats.di

import com.example.mybeats.view.login.LoginViewModel
import com.example.mybeats.view.products.list.ProductsViewModel
import com.example.mybeats.view.signup.SignUpViewModel
import com.example.mybeats.view.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        ProductsViewModel(repository = get())
    }
    viewModel {
        LoginViewModel(usersRepository = get())
    }
    viewModel {
        SignUpViewModel(usersRepository = get())
    }
    viewModel {
        SplashScreenViewModel(usersRepository = get())
    }
}
