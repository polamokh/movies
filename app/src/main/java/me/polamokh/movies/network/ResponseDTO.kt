package me.polamokh.movies.network

import com.squareup.moshi.Json
import me.polamokh.movies.domain.Movie

data class ResponseDTO(
    val page: Int,
    val results: List<Movie>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)
