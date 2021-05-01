package me.polamokh.movies.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import me.polamokh.movies.network.TMDBService
import javax.inject.Inject

class TMDBRepository @Inject constructor(private val service: TMDBService) {

    fun getNowPlayingMovies() = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        MoviesDataSource(service, MovieType.NOW_PLAYING)
    }.liveData

    fun getTopRatedMovies() = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        MoviesDataSource(service, MovieType.TOP_RATED)
    }.liveData

    fun searchMovies(query: String) = Pager(
        config = PagingConfig(DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    ) {
        SearchMoviesDataSource(service, query)
    }.liveData

    suspend fun getMovieDetails(movieId: Int) = service.getDetailsAsync(movieId).await()
}