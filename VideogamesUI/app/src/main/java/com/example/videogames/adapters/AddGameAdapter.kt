package com.example.videogames.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videogames.R
import com.example.videogames.rest.*


class AddGameAdapter(private val dataSet: ArrayList<VideogameDto>, private val userId: Int) :
    RecyclerView.Adapter<AddGameAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.game_row_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name
        Glide.with(viewHolder.itemView)
            .load(dataSet[position].image)
            .centerCrop()
            .into(viewHolder.imageView)

        viewHolder.itemView.setOnClickListener {
            addGameForUser(it, userId, dataSet[position])
        }
    }

    private fun addGameForUser(view: View, userId: Int, videogame: VideogameDto) {
        ApiCaller<Videogame>().call(GameDbApi().gameApiService.addGameForUser(userId,
            convertVideogameFromDto(videogame)
        ),
            onFailure = {
                Toast.makeText(view.context, "Failed load games", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                val bundle = Bundle()
                bundle.putInt("userId", userId)
                Navigation.findNavController(view).navigate(
                    R.id.action_addGameFragment_to_videogameFragment,
                    bundle
                )
            })
    }

    override fun getItemCount() = dataSet.size

    fun addItems(items: ArrayList<VideogameDto>) {
        dataSet.addAll(items)
    }

    fun replaceAll(newGames: ArrayList<VideogameDto>) {
        dataSet.clear()
        dataSet.addAll(newGames)
        notifyDataSetChanged()
    }
}
