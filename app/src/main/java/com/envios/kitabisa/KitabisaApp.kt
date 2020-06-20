package com.envios.kitabisa

import android.app.Application
import com.envios.kitabisa.di.appModules
import com.envios.kitabisa.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KitabisaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KitabisaApp)
            modules(listOf(appModules, dataModules))
        }
    }
}