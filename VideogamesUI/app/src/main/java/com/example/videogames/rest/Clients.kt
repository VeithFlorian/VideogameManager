package com.example.videogames.rest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RawgPublicApi {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.rawg.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val rawgService: RawgService
            by lazy { retrofit.create(RawgService::class.java) }
}

class GameDbApi {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val gameApiService: GameApiService
            by lazy { retrofit.create(GameApiService::class.java) }
}

class ApiCaller<T> {
    fun call(call: Call<T>, onFailure: () -> Unit, onSuccess: (Response<T>) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure()
            }

            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                onSuccess(response)
            }
        })
    }
}