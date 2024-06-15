package com.ujizin.pokedex

import android.app.Application
import com.ujizin.pokedex.di.initKoin

class PokedexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
