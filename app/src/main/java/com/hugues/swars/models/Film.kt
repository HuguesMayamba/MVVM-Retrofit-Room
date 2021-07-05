package com.hugues.swars.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Film(

@PrimaryKey(autoGenerate = true)
var id: Int? = null,
val characters: List<String>,
val created: String,
val director: String,
val edited: String,
val episode_id: Int,
val opening_crawl: String,
val planets: List<String>,
val producer: String,
val release_date: String,
val species: List<String>,
val starships: List<String>,
val title: String,
val url: String,
val vehicles: List<String>
): Serializable