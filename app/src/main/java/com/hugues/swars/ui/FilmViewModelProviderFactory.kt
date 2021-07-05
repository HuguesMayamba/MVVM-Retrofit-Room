package com.hugues.swars.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hugues.swars.repository.FilmRepository

class FilmViewModelProviderFactory(
    val app: Application,
    val filmRepository: FilmRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilmViewModel(app, filmRepository) as T
    }
}