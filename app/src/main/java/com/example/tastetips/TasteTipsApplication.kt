package com.example.tastetips

import android.app.Application
import com.example.tastetips.data.AppContainer
import com.example.tastetips.data.DefaultAppContainer

class TasteTipsApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}