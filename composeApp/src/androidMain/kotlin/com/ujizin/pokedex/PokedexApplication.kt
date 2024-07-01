package com.ujizin.pokedex

import android.app.Application
import androidx.room.Room
import com.ujizin.pokedex.data.local.PokemonDatabase
import com.ujizin.pokedex.di.initKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(module { singleOf(::createDatabase) })
    }

    private fun createDatabase(): PokemonDatabase = Room.databaseBuilder(
        this,
        PokemonDatabase::class.java,
        PokemonDatabase.NAME
    ).build()
}
