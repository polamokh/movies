package me.polamokh.movies.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.polamokh.movies.domain.Movie
import me.polamokh.movies.network.TMDBService

class SearchMoviesDataSource(private val tmdbService: TMDBService, private val query: String) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = tmdbService.search(query, page)

            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}