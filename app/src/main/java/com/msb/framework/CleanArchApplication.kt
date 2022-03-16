package com.msb.framework

import android.app.Application
import com.msb.framework.di.dataSourceModule
import com.msb.framework.di.repositoryModule
import com.msb.framework.di.useCaseModule
import com.msb.framework.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import xyz.teknol.database.SharedPreferenceManager
import xyz.teknol.network.retrofit.RetrofitBuilder

class CleanArchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CleanArchApplication)
            modules(myModule, dataSourceModule, repositoryModule, useCaseModule, viewModelModule)
        }
    }

    private val myModule = module {
        single {
            SharedPreferenceManager(get())
        }
        single {
            RetrofitBuilder(get())
                .enableCache()
                .getApiService()
        }
    }
}