package com.example.videogames.rest

import retrofit2.Call
import retrofit2.http.*


interface RawgService {
    @GET("api/games")
    fun getGames(
        @Query("page") page: String,
        @Query("search") query: String?,
        @Query("key") key: String = "b8c469b9aa6844b09d80e62574ddcb71" //I don't care about this key
    ): Call<GameList>
}

interface GameApiService {
    @POST("users")
    fun createUser(
        @Body() user: User,
    ): Call<User>

    @GET("users")
    fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<User>

    @GET("videogames")
    fun getGamesForUser(
        @Query("userId") userId: Int
    ): Call<List<Videogame>>

    @POST("videogames")
    fun addGameForUser(
        @Query("userId") userId: Int,
        @Body() videogame: Videogame
    ): Call<Videogame>

    @DELETE("videogames")
    fun removeGameForUser(
        @Query("userId") userId: Int,
        @Query("gameId") gameId: Int,
        ): Call<Videogame>
}