package com.example.videogamesui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.Preconditions
import androidx.navigation.NavController
import com.example.videogames.rest.Videogame
import com.skydoves.landscapist.glide.GlideImage

private const val weightHeader = 1f
private const val weightValues = 3f


@Composable
fun GameDetail(navController: NavController, game: Videogame) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = game.name,
                fontSize = 24.sp,
            )
        })
    })
    {
        Column(modifier = Modifier.padding(8.dp)) {
            GlideImage(
                imageModel = game.image,
                loading = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
                failure = { ImageVector.vectorResource(R.drawable.ic_baseline_error_24) },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(256.dp)
                    .padding(vertical = 8.dp),
            )
            Row {
                Text(
                    text = "Genres: ",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightHeader)
                )
                Text(
                    text = game.genres.joinToString(", ") { it.name },
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightValues)
                )
            }
            Row {
                Text(
                    text = "Platforms: ",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightHeader)
                )
                Text(
                    text = game.platforms.joinToString(", ") { it.platform.name },
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightValues)
                )
            }
            Row {
                Text(
                    text = "Metacritic: ",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightHeader)
                )
                Text(
                    text = game.metacritic.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.weight(weightValues)
                )
            }
        }
    }
}