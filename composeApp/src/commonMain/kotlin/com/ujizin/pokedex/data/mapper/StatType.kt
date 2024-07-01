package com.ujizin.pokedex.data.mapper

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
