package com.hugues.swars.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugues.swars.R
import com.hugues.swars.models.Character
import kotlinx.android.synthetic.main.item_film_characters.view.*


class FilmCharacterAdapter(private val characterList: List<Character>): RecyclerView.Adapter<FilmCharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film_characters, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)

        //Log.d("CHARACTER", characterList)

        holder.itemView.apply {


            Glide.with(this)
                .load("https://picsum.photos/seed/picsum/200/300")
                .centerCrop()
                .into(ivFlmCharacterImage)
        }

    }

    override fun getItemCount(): Int  = characterList.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var tvFlmCharacterName = view.findViewById<TextView>(R.id.tvFlmCharacterName)
        private var tvFlmCharacterGender = view.findViewById<TextView>(R.id.tvFlmCharacterGender)
        private var tvFlmCharacterBirth = view.tvFlmCharacterBirth
        fun bind(character: Character) {

            tvFlmCharacterName.text = character.name
            tvFlmCharacterGender.text = character.gender
            tvFlmCharacterBirth.text = character.birth_year
        }
    }

}