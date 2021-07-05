package com.hugues.swars.models

data class DataCharacter(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Character>
)