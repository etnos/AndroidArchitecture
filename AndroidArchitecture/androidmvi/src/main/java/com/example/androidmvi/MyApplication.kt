package com.example.androidmvi

import android.app.Application
import android.content.Context
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        lateinit var globalContext: Context
    }
    override fun onCreate() {
        super.onCreate()

        globalContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}