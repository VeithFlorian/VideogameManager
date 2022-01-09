package com.example.videogamesui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun RegisterUser(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Register")
        })
    })
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val firstname = remember {
                mutableStateOf("")
            }
            val lastname = remember {
                mutableStateOf("")
            }
            val username = remember {
                mutableStateOf("")
            }
            val email = remember {
                mutableStateOf("")
            }
            val password = remember {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = firstname.value,
                onValueChange = { firstname.value = it },
                placeholder = { Text("Firstname") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = lastname.value,
                onValueChange = { lastname.value = it },
                placeholder = { Text("Lastname") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                placeholder = { Text("Username") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text("Email") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = { Text("Password") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Register")
            }
        }
    }
}