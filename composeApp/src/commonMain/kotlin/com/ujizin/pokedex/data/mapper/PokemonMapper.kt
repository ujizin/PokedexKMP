package com.ujizin.pokedex.data.mapper

import com.ujizin.pokedex.data.local.PokemonEntity
import com.ujizin.pokedex.data.mapper.StatType.Companion.getShortName
import com.ujizin.pokedex.data.service.model.PokemonDTO
import com.ujizin.pokedex.data.service.model.PokemonStatsDTO
import com.ujizin.pokedex.data.service.model.PokemonTypeDTO
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.domain.PokemonType

class PokemonMapper {

    fun fromDomainToEntity(pokemon: Pokemon) = PokemonEntity(
        id = pokemon.id,
        name = pokemon.name,
        imageUrl = pokemon.imageUrl,
        types = pokemon.types.map { it.name },
        stats = pokemon.stats.map { PokemonEntity.Stats(it.name, it.base) },
        specialStats = pokemon.specialStats.map { PokemonEntity.Stats(it.name, it.base) },
    )

    fun fromEntityToDomain(pokemon: PokemonEntity) = Pokemon(
        id = pokemon.id,
        name = pokemon.name,
        types = pokemon.types.map { PokemonType(it) },
        imageUrl = pokemon.imageUrl,
        stats = pokemon.stats.map { Pokemon.Stats(it.name, it.base) },
        specialStats = pokemon.specialStats.map { Pokemon.Stats(it.name, it.base) }
    )

    fun fromRemoteToDomain(pokemon: PokemonDTO) = Pokemon(
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

    fun fromPageRemoteToEntity(pokemon: PokemonDTO): PokemonEntity = PokemonEntity(
        id = pokemon.index,
        name = pokemon.name,
        imageUrl = pokemon.imageUrl,
    )
}
