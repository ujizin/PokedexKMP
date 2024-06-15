package com.ujizin.pokedex.di

import com.ujizin.pokedex.di.AppModule.appModules
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(appModules)
    }
}
