package com.example.videogames.rest

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

class RestClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.rawg.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val gameApiService: GameApiService
            by lazy { retrofit.create(GameApiService::class.java) }
}

interface GameApiService {
    @GET("api/games")
    fun getGames(
        @Query("page") page: String,
        @Query("search") query: String?,
        @Query("key") key: String = "b8c469b9aa6844b09d80e62574ddcb71" //I don't care about this key
    ): Call<GameList>
}

data class GameList(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "results") val games: List<Videogame>,
    @field:Json(name = "next") val nextPage: String?
) : Serializable

data class Videogame(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "rating") val rating: Float,
    @field:Json(name = "metacritic") val metacritic: Int?,
    @field:Json(name = "genres") val genres: List<Genre>,
    @field:Json(name = "platforms") val platforms: List<PlatformDetail>,
    @field:Json(name = "background_image") val image: String,
    ) : Serializable

data class PlatformDetail(
    @field:Json(name = "platform") val platform: Platform
)

data class Platform(
    @field:Json(name = "name") val name: String
    )

data class Genre(
    @field:Json(name = "name") val name: String
    )

