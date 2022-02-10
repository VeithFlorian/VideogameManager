package com.example.videogames.rest

import com.squareup.moshi.Json
import java.io.Serializable

data class GameList(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "results") val games: List<VideogameDto>,
    @field:Json(name = "next") val nextPage: String?
) : Serializable

data class VideogameDto(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "rating") val rating: Float,
    @field:Json(name = "metacritic") val metacritic: Int?,
    @field:Json(name = "genres") val genres: List<Genre>,
    @field:Json(name = "platforms") val platforms: List<PlatformDetail>,
    @field:Json(name = "background_image") val image: String,
) : Serializable

data class Videogame(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "rating") val rating: Float,
    @field:Json(name = "metacritic") val metacritic: Int?,
    @field:Json(name = "genres") val genres: List<Genre>,
    @field:Json(name = "platforms") val platforms: List<Platform>,
    @field:Json(name = "image") val image: String,
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

data class User(
    @field:Json(name = "id") val id : Int = 0,
    @field:Json(name = "firstname") val firstname : String,
    @field:Json(name = "lastname") val lastname : String,
    @field:Json(name = "username") val username : String,
    @field:Json(name = "email") val email : String,
    @field:Json(name = "password") val password : String,
    @field:Json(name = "videogames") val videogames: List<VideogameDto> = ArrayList(),
)


fun convertVideogameToDto(videogameDto: Videogame): VideogameDto {
    return VideogameDto(
        videogameDto.name,
        videogameDto.rating,
        videogameDto.metacritic,
        videogameDto.genres,
        videogameDto.platforms.map { platform ->
            PlatformDetail(platform)
        },
        videogameDto.image
    )
}

fun convertVideogameFromDto(videogame: VideogameDto): Videogame {
    return Videogame(
        0,
        videogame.name,
        videogame.rating,
        videogame.metacritic,
        videogame.genres,
        videogame.platforms.map { platformDetail ->
            Platform(platformDetail.platform.name)
        },
        videogame.image
    )
}