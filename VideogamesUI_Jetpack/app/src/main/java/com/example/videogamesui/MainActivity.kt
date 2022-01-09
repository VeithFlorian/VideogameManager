package com.example.videogamesui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videogames.rest.Videogame
import com.example.videogamesui.ui.theme.VideogamesUI_JetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideogamesUI_JetpackTheme {
                val navController = rememberNavController()

                val gameList = remember {
                    mutableStateListOf<Videogame>()
                }
                for (i in 0..100) {
                    gameList.add(Videogame("Videogame$i", 0f, 0, ArrayList(), ArrayList(), ""))
                }
                NavHost(navController, startDestination = "login") {
                    composable(route = "login") {
                        Login(navController = navController)
                    }
                    composable(route = "register") {
                        RegisterUser(navController = navController)
                    }
                    composable(route = "gamelist") {
                        GameList(navController = navController, gameList)
                    }
                    composable(route = "addGame") {
                        AddGame(navController = navController, gameList)
                    }
                    composable(route = "gameDetail/{gameIndex}") {
                        GameDetail(navController = navController, gameList[it.arguments?.getString("gameIndex")!!.toInt()])
                    }
                }
            }
        }
    }
}