package com.hugues.swars.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hugues.swars.R
import com.hugues.swars.ui.FilmActivity
import com.hugues.swars.ui.FilmViewModel
import kotlinx.android.synthetic.main.fragment_detail_characters.*


class CharacterDetailFragment : Fragment(R.layout.fragment_detail_characters) {

    lateinit var viewModel: FilmViewModel
    val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as FilmActivity).viewModel
        val character = listOf(args.character)

        Glide.with(this)
            .load("https://homepages.cae.wisc.edu/~ece533/images/pool.png")
            .centerCrop()
            .into(ivCharacterImage)
        tvCharacterName.text = character[0].name
        tvCharacterGender.text = character[0].gender
        tvCharacterBirth.text = character[0].birth_year
        tvAltura.text = character[0].height

    }
}