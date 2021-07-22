package com.hugues.swars.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hugues.swars.FilmApplication
import com.hugues.swars.models.Film
import com.hugues.swars.models.DataCharacter
import com.hugues.swars.models.DataFilm
import com.hugues.swars.repository.FilmRepository
import com.hugues.swars.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class FilmViewModel(
    app: Application,
    val filmRepository: FilmRepository
) : AndroidViewModel(app) {

    val film: MutableLiveData<Resource<DataFilm>> = MutableLiveData()
    val character: MutableLiveData<Resource<DataCharacter>> = MutableLiveData()
    var filmPage = 1
    var filmDataFilm: DataFilm? = null
    var charactersResponse: DataCharacter? = null
    var filmSafe: Film? = null
    val characterList = mutableListOf<String>()

    init {
        getFilm()
        getCharacter()

    }

    fun getFilm() = viewModelScope.launch {

        film.postValue(Resource.Loading())
        val response = filmRepository.getFilms()
        film.postValue(handleFilmResponse(response))
    }
    fun getCharacter() = viewModelScope.launch {
        character.postValue(Resource.Loading())
        val response = filmRepository.getCharacter()
        character.postValue(handleCharacterResponse(response))

    }

    private fun handleFilmResponse(response: Response<DataFilm>) : Resource<DataFilm> {

        if(response.isSuccessful) {

                var filmsaved = response.body()?.results

            if (filmsaved != null) {
                for (i in filmsaved){
                    filmSafe = i
                }
                filmSafe?.let { saveFilm(it) }
               // Log.d("FILMSAFE", "$filmSafe")
            }

            response.body()?.let { resultResponse ->
                filmPage++
                if(filmDataFilm == null) {
                    filmDataFilm = resultResponse
                }
                else {
                    val oldArticles = filmDataFilm?.results
                    val newArticles = resultResponse.results

                    oldArticles?.plusElement(newArticles)

                }
                return Resource.Success(filmDataFilm ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleCharacterResponse(response: Response<DataCharacter>) : Resource<DataCharacter> {
        if(response.isSuccessful) {

            response.body()?.let { resultResponse ->
                filmPage++
                if(charactersResponse == null) {
                    charactersResponse = resultResponse

                } else {
                    val oldArticles = charactersResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.plusElement(newArticles)

                }
                return Resource.Success(charactersResponse ?: resultResponse)

            }
        }
        return Resource.Error(response.message())
    }


    fun saveFilm(film: Film) = viewModelScope.launch {

        filmRepository.upsert(film)
    }

    fun deleteArticle(film: Film) = viewModelScope.launch {
        filmRepository.deleteArticle(film)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<FilmApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}