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
import com.example.videogames.rest.GameList
import com.example.videogames.rest.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                query = if(filter == "") null else filter
                getGames(query)
                return true
            }

            override fun onQueryTextChange(filter: String?): Boolean {
                if(filter == "" || filter == null) {
                    getGames(null)
                }
                return true
            }
        })
    }


    private var nextPage: Int? = 1
    private var query: String? = null
    private val addGameAdapter: AddGameAdapter = AddGameAdapter(ArrayList())

    private fun getGames(query: String?, loadNextGames: Boolean = false) {
        if(nextPage == null) {
            Toast.makeText(requireView().context, "Last", Toast.LENGTH_LONG).show()
            return
        }
        if(!loadNextGames) {
            nextPage = 1
        }

        val call = RestClient().gameApiService.getGames(nextPage!!.toString(), query)
        call.enqueue(object : Callback<GameList> {
            override fun onFailure(call: Call<GameList>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }
            override fun onResponse(
                call: Call<GameList>,
                response: Response<GameList>
            ) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Received games $query")
                    val newItems = response.body()!!.games as ArrayList
                    val oldItemCount = addGameAdapter.itemCount
                    if(loadNextGames) {
                        addGameAdapter.addItems(newItems)
                    }
                    else {
                        addGameAdapter.replaceAll(newItems)
                    }
                    view!!.findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                    addGameAdapter.notifyItemRangeChanged(oldItemCount, newItems.size)

                    nextPage = if(response.body()!!.nextPage == null) {
                        null
                    } else {
                        nextPage!! + 1
                    }
                } else {
                    Log.e(
                        "MainActivity",
                        "Failed to get search results\n${response.errorBody()?.string() ?: ""}"
                    )
                }
            }
        })
    }
}