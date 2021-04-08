package me.polamokh.movies.domain

import com.squareup.moshi.Json

open class Movie(
    open val id: Int,
    open val title: String,
    @Json(name = "vote_average")
    open val voteAverage: Float,
    @Json(name = "poster_path")
    open val imagePath: String?
) {
    val imageBaseUrl
        get() = "https://image.tmdb.org/t/p/original${imagePath}"
}
