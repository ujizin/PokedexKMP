package com.ujizin.pokedex.data.mapper

import com.ujizin.pokedex.data.mapper.StatType.Companion.getShortName
import com.ujizin.pokedex.data.service.model.PokemonDTO
import com.ujizin.pokedex.data.service.model.PokemonStatsDTO
import com.ujizin.pokedex.data.service.model.PokemonTypeDTO
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.domain.PokemonType

class PokemonMapper {

    fun toDomain(pokemons: List<PokemonDTO>): List<Pokemon> = pokemons.map(::toDomain)

    fun toDomain(pokemon: PokemonDTO) = Pokemon(
        id = pokemon.index,
        name = pokemon.name,
        types = pokemon.types.map { toDomain(it) },
        imageUrl = pokemon.imageUrl,
        stats = pokemon.stats.mapNotNull {
            toDomainStats(it) { stat -> !StatType.isSpecial(stat) }
        },
        specialStats = pokemon.stats.mapNotNull { toDomainStats(it, StatType::isSpecial) }
    )

    private fun toDomain(type: PokemonTypeDTO) = PokemonType(name = type.type.name)

    private fun toDomainStats(stats: PokemonStatsDTO, predicate: (String) -> Boolean) = when {
        predicate(stats.stat.name) -> Pokemon.Stats(getShortName(stats.stat.name), stats.baseStat)
        else -> null
    }
}

enum class StatType(
    val statName: String,
    val shortName: String,
    val isSpecial: Boolean = false,
) {
    HP("HP", "HP"),
    ATTACK("attack", "ATK"),
    DEFENSE("defense", "DEF"),
    SPEED("speed", "SPD"),
    SPECIAL_ATTACK("special-attack", "SPD-ATK", true),
    SPECIAL_DEFENSE("special-defense", "SPD-DEF", true);

    companion object {
        fun isSpecial(name: String) = StatType.entries.find {
            it.statName.contains(name, true)
        }?.isSpecial ?: false

        fun getShortName(name: String) = StatType.entries.find {
            it.statName.contains(name, true)
        }?.shortName ?: name
    }
}
