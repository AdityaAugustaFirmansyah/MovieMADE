package com.aditya.moviemade

import android.app.Application
import com.aditya.moviemade.di.AppModule.useCaseModule
import com.aditya.moviemade.di.AppModule.viewModelModule
import com.aditya.core.di.CoreModule.databaseModule
import com.aditya.core.di.CoreModule.networkModule
import com.aditya.core.di.CoreModule.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repoModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}