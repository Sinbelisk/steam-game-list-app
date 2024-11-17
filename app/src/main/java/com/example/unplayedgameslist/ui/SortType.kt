package com.example.unplayedgameslist.ui

/**
 * Enum class representing the sorting types for the list of games based on playtime.
 * It allows sorting the games either by the least played hours (ASC) or the most played hours (DESC).
 *
 * @property label The display label associated with each sorting type for user-friendly descriptions.
 */
enum class SortType(val label: String) {
    ASC("Menos horas jugadas"),  // Sorting by less played hours
    DESC("Más horas jugadas")   // Sorting by more played hours
}