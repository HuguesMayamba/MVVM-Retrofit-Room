package com.hugues.swars.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hugues.swars.models.Film

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(film: Film): Long

    @Query("SELECT * FROM articles")
    fun getAllFilms(): LiveData<List<Film>>


    @Delete
    suspend fun deleteFilm(film: Film)

    @Delete
    suspend fun deleteCharacter(film: Film)

}