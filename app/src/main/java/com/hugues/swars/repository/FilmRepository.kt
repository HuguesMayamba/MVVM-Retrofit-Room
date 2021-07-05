package com.hugues.swars.repository

import com.hugues.swars.api.RetrofitInstance
import com.hugues.swars.db.FilmDatabase
import com.hugues.swars.models.Film

class FilmRepository(
    val db: FilmDatabase
) {
    suspend fun getFilms() =
        RetrofitInstance.api.getFilms()


    suspend fun getCharacter() = RetrofitInstance.api.getCharacter()

    suspend fun upsert(film: Film) = db.getFilmDao().upsert(film)

    fun getSavedNews() = db.getFilmDao().getAllFilms()

    suspend fun deleteArticle(film: Film) = db.getFilmDao().deleteFilm(film)
}