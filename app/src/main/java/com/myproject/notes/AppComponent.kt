package com.myproject.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppComponent : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}