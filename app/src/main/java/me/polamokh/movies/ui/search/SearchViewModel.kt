package me.polamokh.movies.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.polamokh.movies.repo.TMDBRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val tmdbRepository: TMDBRepository) :
    ViewModel() {

    private val query = MutableLiveData<String>()

    val movies = query.switchMap { searchQuery ->
        tmdbRepository.searchMovies(searchQuery).cachedIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        this.query.value = query
    }
}