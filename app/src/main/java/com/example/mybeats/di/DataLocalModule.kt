package com.example.mybeats.di

import com.example.mybeats.data.local.database.UsersDataBase
import com.example.mybeats.data.repository.UsersRepository
import com.example.mybeats.data.repository.UsersRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataModule = module {
    single { UsersDataBase.createDataBase(androidContext()) }
    factory<UsersRepository> { UsersRepositoryImpl(get(), androidContext()) }
}
