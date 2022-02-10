package com.example.videogames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.adapters.AddGameAdapter
import com.example.videogames.rest.ApiCaller
import com.example.videogames.rest.GameList
import com.example.videogames.rest.RawgPublicApi

class AddGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    private var nextPage: Int? = 1
    private var userId: Int = -1;
    private var query: String? = null
    private lateinit var addGameAdapter: AddGameAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = requireArguments()
        if (arguments.containsKey("userId")) {
            userId = arguments.getInt("userId")
            addGameAdapter = AddGameAdapter(ArrayList(), userId)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rvGameList)
        recyclerView.adapter = addGameAdapter
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        getGames(null)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getGames(query, true)
                }
            }
        })

        view.findViewById<SearchView>(R.id.svFilter).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(filter: String?): Boolean {
                Log.d("MainActivity", "Filter games")
                query = if (filter == "") null else filter
                getGames(query)
                return true
            }

            override fun onQueryTextChange(filter: String?): Boolean {
                if (filter == "" || filter == null) {
                    getGames(null)
                }
                return true
            }
        })
    }

    private fun getGames(query: String?, loadNextGames: Boolean = false) {
        if (nextPage == null) {
            Toast.makeText(requireView().context, "Last", Toast.LENGTH_LONG).show()
            return
        }
        if (!loadNextGames) {
            nextPage = 1
        }

        ApiCaller<GameList>().call(RawgPublicApi().rawgService.getGames(
            nextPage!!.toString(),
            query
        ), onFailure = {
            Toast.makeText(context, "Failed to get search results", Toast.LENGTH_SHORT).show()
        }, onSuccess = {
            val newItems = it.body()!!.games as ArrayList
            val oldItemCount = addGameAdapter.itemCount
            if (loadNextGames) {
                addGameAdapter.addItems(newItems)
            } else {
                addGameAdapter.replaceAll(newItems)
            }
            requireView().findViewById<ProgressBar>(R.id.progress_circular)!!.visibility =
                View.GONE
            addGameAdapter.notifyItemRangeChanged(oldItemCount, newItems.size)

            nextPage = if (it.body()!!.nextPage == null) {
                null
            } else {
                nextPage!! + 1
            }
        })
    }
}
