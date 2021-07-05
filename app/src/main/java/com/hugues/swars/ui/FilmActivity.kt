package com.hugues.swars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hugues.swars.R
import com.hugues.swars.db.FilmDatabase
import com.hugues.swars.repository.FilmRepository
import kotlinx.android.synthetic.main.activity_film.*

class FilmActivity : AppCompatActivity() {

    lateinit var viewModel: FilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)

        val newsRepository = FilmRepository(FilmDatabase(this))
        val viewModelProviderFactory = FilmViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(FilmViewModel::class.java)
        bottomNavigationView.setupWithNavController(NavHostFragment.findNavController())

    }
}
