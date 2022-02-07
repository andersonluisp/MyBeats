package com.example.mybeats

import android.app.Application
import com.example.mybeats.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyBeatsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyBeatsApplication)
            modules(mainModule)
        }
    }

}