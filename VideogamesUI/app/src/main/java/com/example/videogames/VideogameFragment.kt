package com.example.videogames

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.adapters.VideoGameAdapter
import com.example.videogames.rest.Videogame

class VideogameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videogame, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videogames: ArrayList<Videogame> = ArrayList()
        for (i in 0..100) {
            videogames.add(Videogame("Videogame$i", 0f, 0, ArrayList(), ArrayList(), ""))
        }

        val arguments = requireArguments()
        if(arguments.containsKey("game")) {
            Log.d("MainActivity", "AddGame")
            videogames.add(0, arguments.get("game") as Videogame)
        }

        val rvGameList = view.findViewById<RecyclerView>(R.id.rvGameList)
        val adapter = VideoGameAdapter(videogames.clone() as ArrayList<Videogame>);
        rvGameList.adapter = adapter
        rvGameList.layoutManager = LinearLayoutManager(view.context)

        val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_videogameFragment_to_addGameFragment
            )
        }

        view.findViewById<SearchView>(R.id.svFilter).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return adapter.filter(query, videogames)
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return adapter.filter(query, videogames)
            }
        })
    }

}