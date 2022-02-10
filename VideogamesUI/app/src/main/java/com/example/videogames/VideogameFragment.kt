package com.example.videogames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.adapters.VideoGameAdapter
import com.example.videogames.rest.*

class VideogameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videogame, container, false)
    }

    val videogames: ArrayList<Videogame> = ArrayList()
    lateinit var adapter: VideoGameAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = requireArguments()
        val userId: Int
        if (arguments.containsKey("userId")) {
            userId = arguments.getInt("userId")
            getGamesForUser(view, userId)
        } else {
            userId = -1
        }

        val rvGameList = view.findViewById<RecyclerView>(R.id.rvGameList)
        adapter = VideoGameAdapter(videogames.clone() as ArrayList<Videogame>, userId);
        rvGameList.adapter = adapter
        rvGameList.layoutManager = LinearLayoutManager(view.context)

        val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
        val bundle = Bundle()
        bundle.putInt("userId", userId)
        btnAdd.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_videogameFragment_to_addGameFragment,
                bundle
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

    private fun getGamesForUser(view: View, userId: Int) {
        ApiCaller<List<Videogame>>().call(
            GameDbApi().gameApiService.getGamesForUser(userId),
            onFailure = {
                Toast.makeText(context, "Failed load games", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                videogames.clear()
                videogames.addAll(it.body()!!)
                adapter.replaceAll(it.body()!!)
                adapter.notifyItemRangeChanged(0, videogames.size)
            }
        )
    }

}