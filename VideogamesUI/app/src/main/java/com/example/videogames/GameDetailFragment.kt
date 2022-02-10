package com.example.videogames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.videogames.rest.Videogame

class GameDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewGameTitle: TextView = view.findViewById(R.id.textViewGameTitle)
        val textViewGenres: TextView = view.findViewById(R.id.textViewGenres)
        val textViewPlatforms: TextView = view.findViewById(R.id.textViewPlatforms)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val textViewMetacritic: TextView = view.findViewById(R.id.ratingMetacritic)
        val imageViewGameImage: ImageView = view.findViewById(R.id.imageViewGameImage)


        val game = requireArguments().get("game") as Videogame

        textViewGameTitle.text = game.name
        Glide.with(imageViewGameImage)
            .load(game.image)
            .centerCrop()
            .into(imageViewGameImage)
        textViewGenres.text = game.genres.joinToString(", ") { it.name }
        textViewPlatforms.text = game.platforms.joinToString(", ") { it.name }
        if(game.metacritic != null) {
            textViewMetacritic.text = game.metacritic.toString();
        }
        ratingBar.rating = game.rating
    }
}