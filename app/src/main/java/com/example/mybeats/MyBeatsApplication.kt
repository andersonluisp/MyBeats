package com.example.mybeats

import android.app.Application
import com.example.mybeats.di.localDataModule
import com.example.mybeats.di.mainModule
import com.example.mybeats.di.remoteDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("UNUSED")
class MyBeatsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyBeatsApplication)
            modules(mainModule + remoteDataModule + localDataModule)
        }
    }
}
