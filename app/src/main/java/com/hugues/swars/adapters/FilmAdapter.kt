package com.hugues.swars.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugues.swars.R
import com.hugues.swars.models.Film
import kotlinx.android.synthetic.main.item_film_preview.view.*

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_film_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Film) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {

            Glide.with(this)
                .load("https://homepages.cae.wisc.edu/~ece533/images/pool.png")
                .centerCrop()
                .into(ivFlm)
            tvFlmTitle.text = article.title
            tvFlmDescription.text = article.opening_crawl
            tvFlmYear.text = article.release_date

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Film) -> Unit) {
        onItemClickListener = listener
    }
}