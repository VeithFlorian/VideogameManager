package com.example.videogamesui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.example.videogames.rest.GameList
import com.example.videogames.rest.RestClient
import com.example.videogames.rest.Videogame
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddGame(navController: NavController, gameList: SnapshotStateList<Videogame>) {
    val queriedGames = remember {
        mutableStateListOf<Videogame>()
    }
    val searchText = remember {
        mutableStateOf("")
    }
    getGames(null, false, queriedGames)
    Log.d("AddGame", queriedGames.toString())

    Scaffold() {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Column {
            val endOfListReached by remember {
                derivedStateOf {
                    listState.isScrolledToEnd()
                }
            }
            Row {
                SearchBar(
                    searchText = searchText.value,
                    placeholderText = "Search games",
                    onClearClick = {
                        searchText.value = ""
                        getGames(null, false, queriedGames)
                        scrollToTop(coroutineScope, listState)
                    },
                    onSearchTextChanged = {
                        searchText.value = it
                    },
                    onDone = {
                        val query = if (searchText.value == "") null else searchText.value
                        getGames(query, false, queriedGames)
                        scrollToTop(coroutineScope, listState)
                    }
                )
                LaunchedEffect(endOfListReached) {
                    val query = if (searchText.value == "") null else searchText.value
                    getGames(query, true, queriedGames)
                }
            }
            LazyVerticalGrid(
                state = listState,
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                items(queriedGames) {
                    Box(modifier = Modifier
                        .clickable {
                            gameList.add(0, it)
                            navController.navigate("gameList") {
                                popUpTo("addGame") { inclusive = true }
                            }
                        }
                        .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            GlideImage(
                                imageModel = it.image,
                                loading = {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(
                                            Center
                                        )
                                    )
                                },
                                failure = { ImageVector.vectorResource(R.drawable.ic_baseline_error_24) },
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.height(128.dp),
                            )
                            Text(
                                it.name,
                                fontSize = 24.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

private var nextPage: Int? = 1

private fun getGames(
    query: String?,
    loadNextGames: Boolean = false,
    gameList: SnapshotStateList<Videogame>
) {
    if (nextPage == null) {
        return
    }
    if (!loadNextGames) {
        nextPage = 1
    }

    val call = RestClient().gameApiService.getGames(nextPage!!.toString(), query)
    call.enqueue(object : Callback<GameList> {
        override fun onFailure(call: Call<GameList>, t: Throwable) {
            Log.e("AddGame", "Failed to get search results", t)
        }

        override fun onResponse(
            call: Call<GameList>,
            response: Response<GameList>
        ) {
            if (response.isSuccessful) {
                val newItems = response.body()!!.games as ArrayList
                if (!loadNextGames) {
                    gameList.clear()
                }
                gameList.addAll(newItems)
                nextPage = if (response.body()!!.nextPage == null) {
                    null
                } else {
                    nextPage!! + 1
                }
            } else {
                Log.e(
                    "AddGame",
                    "Failed to get search results\n${response.errorBody()?.string() ?: ""}"
                )
            }
        }
    })
}