package com.example.videogames.adapters

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.R
import com.example.videogames.rest.Videogame

class VideoGameAdapter(private val dataSet: ArrayList<Videogame>) :
    RecyclerView.Adapter<VideoGameAdapter.ViewHolder>() {

    private val originalSize = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        //val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name
        val detailBundle = Bundle()
        detailBundle.putSerializable("game", dataSet[position])
        viewHolder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.gameDetailFramgnet, detailBundle)
        )
        viewHolder.btnDelete.setOnClickListener {
            dataSet.removeAt(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemCount);
        }
    }

    override fun getItemCount() = dataSet.size

    fun filter(query: String?, videogames: ArrayList<Videogame> ) : Boolean {
        Log.d("MainActivity", "Filter changed")
        if (query != "" && query != null) {
            replaceAll(videogames.filter { videogame -> videogame.name.lowercase().contains(query.lowercase()) } as ArrayList<Videogame>)
            return true
        }
        Log.d("MainActivity", "videogames ${videogames.size} ")
        replaceAll(videogames)
        return true
    }

    private fun replaceAll(newGames: ArrayList<Videogame> ) {
        dataSet.clear()
        dataSet.addAll(newGames)
        notifyDataSetChanged()
    }
}
