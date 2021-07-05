package com.hugues.swars.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "characters"
)
data class Character(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val birth_year: String,
    val created: String,
    val edited: String,
    val eye_color: String,
    val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
    val species: List<String>,
    val starships: List<String>,
    var url: String,
    val vehicles: List<String>
): Serializable