package com.hugues.swars.models

data class DataFilm(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Film>
)