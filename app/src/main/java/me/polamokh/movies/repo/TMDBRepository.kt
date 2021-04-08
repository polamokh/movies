package me.polamokh.movies.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import me.polamokh.movies.network.TMDBService

private const val DEFAULT_PAGE_SIZE = 20

class TMDBRepository(private val tmdbService: TMDBService) {

    fun getNowPlayingMovies() = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        MoviesDataSource(tmdbService, MovieType.NOW_PLAYING)
    }.liveData

    fun getTopRatedMovies() = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        MoviesDataSource(tmdbService, MovieType.TOP_RATED)
    }.liveData

    fun searchMovies(query: String) = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        SearchMoviesDataSource(tmdbService, query)
    }.liveData
}