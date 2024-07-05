package com.ujizin.pokedex.presentation.utils

import androidx.compose.ui.graphics.Color
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.domain.PokemonType
import com.ujizin.pokedex.presentation.themes.Blue200
import com.ujizin.pokedex.presentation.themes.Blue500
import com.ujizin.pokedex.presentation.themes.Brown200
import com.ujizin.pokedex.presentation.themes.Brown800
import com.ujizin.pokedex.presentation.themes.Green500
import com.ujizin.pokedex.presentation.themes.Green700
import com.ujizin.pokedex.presentation.themes.Pink200
import com.ujizin.pokedex.presentation.themes.Pink500
import com.ujizin.pokedex.presentation.themes.Purple100
import com.ujizin.pokedex.presentation.themes.Purple400
import com.ujizin.pokedex.presentation.themes.Purple500
import com.ujizin.pokedex.presentation.themes.Purple600
import com.ujizin.pokedex.presentation.themes.Purple700
import com.ujizin.pokedex.presentation.themes.Red500
import com.ujizin.pokedex.presentation.themes.Red600
import com.ujizin.pokedex.presentation.themes.Yellow200
import com.ujizin.pokedex.presentation.themes.Yellow500
import com.ujizin.pokedex.presentation.themes.Yellow700

val PokemonType.color: Color
    get() = with(name) {
        when {
            contains("normal") -> Brown200
            contains("fire") -> Red500
            contains("water") -> Blue500
            contains("electric") -> Yellow500
            contains("grass") -> Green500
            contains("ice") -> Blue200
            contains("fighting") -> Red600
            contains("poison") -> Purple500
            contains("ground") -> Yellow200
            contains("flying") -> Purple400
            contains("psychic") -> Pink500
            contains("bug") -> Green700
            contains("rock") -> Yellow700
            contains("ghost") -> Purple600
            contains("dragon") -> Purple700
            contains("dark") -> Brown800
            contains("steel") -> Purple100
            contains("fairy") -> Pink200
            else -> Color.LightGray
        }
    }

fun Pokemon.Stats.toColor() = with(name) {
    when {
        contains("HP") -> Green500
        contains("ATK") -> Red600
        contains("DEF") -> Blue500
        contains("SPD") -> Brown200
        else -> Brown800
    }
}