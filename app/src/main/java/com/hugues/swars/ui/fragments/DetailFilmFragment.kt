package com.hugues.swars.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugues.swars.R
import com.hugues.swars.adapters.FilmCharacterAdapter
import com.hugues.swars.ui.FilmActivity
import com.hugues.swars.ui.FilmViewModel
import com.hugues.swars.util.Constants.Companion.QUERY_PAGE_SIZE
import com.hugues.swars.util.Resource
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_detail_film.*
import kotlinx.android.synthetic.main.item_characters.*
import kotlinx.android.synthetic.main.item_error_message.*


class DetailFilmFragment : Fragment(R.layout.fragment_detail_film) {

    lateinit var viewModel: FilmViewModel
    lateinit var filmCharacterAdapter: FilmCharacterAdapter
    val args: DetailFilmFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as FilmActivity).viewModel

        val film = listOf(args.film)

        Glide.with(this)
            .load("https://homepages.cae.wisc.edu/~ece533/images/pool.png")
            .centerCrop()
            .into(ivDetailFlm)
        tvTitleDetailFlm.text = film[0].title
        tvDescriptionDetailFlm.text = film[0].opening_crawl
        tvYearDetailFlm.text = film[0].release_date


        setupRecyclerView()

        viewModel.character.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->
                        filmCharacterAdapter.differ.submitList(newsResponse.results.toList())

                        if (isLastPage) {
                            rvFilmCharacters.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

        fun hideProgressBar() {
           // paginationProgressBar.visibility = View.INVISIBLE
            isLoading = false
        }

        fun showProgressBar() {
            paginationProgressBar.visibility = View.VISIBLE
            isLoading = true
        }

        fun hideErrorMessage() {
           // itemErrorMessage.visibility = View.INVISIBLE
            isError = false
        }

        fun showErrorMessage(message: String) {
            itemErrorMessage.visibility = View.VISIBLE
            tvErrorMessage.text = message
            isError = true
        }

        var isError = false
        var isLoading = false
        var isLastPage = false
        var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getFilm()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        filmCharacterAdapter = FilmCharacterAdapter()
        rvFilmCharacters.apply {
            adapter = filmCharacterAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@DetailFilmFragment.scrollListener)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}