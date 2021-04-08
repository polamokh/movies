package me.polamokh.movies.domain

import com.squareup.moshi.Json

data class DetailedMovie(
    override val id: Int,
    override val title: String,
    @Json(name = "overview")
    val description: String?,
    @Json(name = "vote_average")
    override val voteAverage: Float,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "poster_path")
    override val imagePath: String?,
    val genres: List<Genre>
) : Movie(id, title, voteAverage, imagePath)
