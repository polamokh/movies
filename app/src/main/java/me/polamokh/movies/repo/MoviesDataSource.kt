package me.polamokh.movies.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.polamokh.movies.domain.Movie
import me.polamokh.movies.network.TMDBService

class MoviesDataSource(
    private val tmdbService: TMDBService,
    private val movieType: MovieType
) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = when (movieType) {
                MovieType.NOW_PLAYING -> tmdbService.getNowPlaying(page)
                MovieType.TOP_RATED -> tmdbService.getTopRated(page)
            }

            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}