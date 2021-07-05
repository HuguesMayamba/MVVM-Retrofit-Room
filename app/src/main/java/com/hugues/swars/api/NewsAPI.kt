package com.hugues.swars.api

import com.hugues.swars.models.DataCharacter
import com.hugues.swars.models.DataFilm
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsAPI {

    @GET("films")
    suspend fun getFilms(): Response<DataFilm>

    @GET()
    suspend fun getFilms(@Url Url: String):Response<DataFilm>

    @GET("people")
    suspend fun getCharacter(): Response<DataCharacter>
    @GET("people")
    suspend fun getCharacter(@Query("page") page: Int): Response<DataCharacter>
}