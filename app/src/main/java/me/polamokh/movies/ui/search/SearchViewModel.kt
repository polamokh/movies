package me.polamokh.movies.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import me.polamokh.movies.repo.TMDBRepository

class SearchViewModel(private val tmdbRepository: TMDBRepository) : ViewModel() {

    private val query = MutableLiveData<String>()

    val movies = query.switchMap { searchQuery ->
        tmdbRepository.searchMovies(searchQuery).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        this.query.value = query
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val tmdbRepository: TMDBRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java))
                return SearchViewModel(tmdbRepository) as T
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}