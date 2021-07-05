package com.hugues.swars.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugues.swars.R
import com.hugues.swars.models.Character
import kotlinx.android.synthetic.main.item_film_characters.view.*

class FilmCharacterAdapter: RecyclerView.Adapter<FilmCharacterAdapter.FilmViewHolder>() {

    inner class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_film_characters,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.itemView.apply {
             Glide.with(this).load("https://homepages.cae.wisc.edu/~ece533/images/pool.png").into(ivFlmCharacterImage)
            tvFlmCharacterName.text = character.name
            tvFlmCharacterGender.text = character.gender
            tvFlmCharacterBirth.text = character.birth_year

            setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }
}