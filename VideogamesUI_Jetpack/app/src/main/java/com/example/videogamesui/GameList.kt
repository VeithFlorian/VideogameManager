package com.example.videogamesui

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.videogames.rest.Videogame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun GameList(navController: NavController, gameList: SnapshotStateList<Videogame>) {
    val gameListFiltered = ArrayList<Videogame>(gameList)

    val searchText = remember {
        mutableStateOf("")
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("addGame") }) {
            Icon(Icons.Filled.Add, "Add")
        }
    }) {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Column {
            Row {
                SearchBar(
                    searchText = searchText.value,
                    placeholderText = "Search games",
                    onClearClick = {
                        searchText.value = ""
                        gameListFiltered.clear()
                        gameListFiltered.addAll(gameList)
                        scrollToTop(coroutineScope, listState)
                    },
                    onSearchTextChanged = {
                        searchText.value = it
                        gameListFiltered.clear()
                        gameListFiltered.addAll(gameList.filter { game ->
                            game.name.lowercase().contains(searchText.value.lowercase())
                        })
                        scrollToTop(coroutineScope, listState)
                    }
                )
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                items(gameListFiltered) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Box(contentAlignment = AbsoluteAlignment.CenterLeft,
                            modifier = Modifier
                            .align(CenterVertically)
                            .weight(1f)
                            .fillParentMaxHeight()
                            .clickable {
                                val index = gameListFiltered.indexOf(it)
                                navController.navigate("gameDetail/${index}")
                            }
                        ) {
                            Text(
                                it.name,
                                fontSize = 18.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                gameListFiltered.remove(it)
                                gameList.remove(it)
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            }
        }
    }
}

fun scrollToTop(coroutineScope: CoroutineScope, listState: LazyListState) {
    coroutineScope.launch {
        listState.animateScrollToItem(0, 0)
    }
}