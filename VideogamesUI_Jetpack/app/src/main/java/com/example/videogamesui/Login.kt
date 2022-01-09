package com.example.videogamesui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Login")
        })
    })
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            var username = ""
            var password = ""
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("Register")
                }
                Button(
                    onClick = { navController.navigate("gamelist") },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("Login")
                }
            }
        }
    }
}
