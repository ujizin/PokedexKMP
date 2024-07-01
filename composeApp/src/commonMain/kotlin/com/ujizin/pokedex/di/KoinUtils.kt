package com.ujizin.pokedex.di

import com.ujizin.pokedex.di.AppModule.appModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(vararg nativeModules: Module){
    startKoin {
        modules(*nativeModules)
        modules(appModules)
    }
}
