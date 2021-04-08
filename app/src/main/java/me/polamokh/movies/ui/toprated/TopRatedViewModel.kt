package me.polamokh.movies.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import me.polamokh.movies.repo.TMDBRepository

class TopRatedViewModel(tmdbRepository: TMDBRepository) : ViewModel() {

    val movies = tmdbRepository.getTopRatedMovies().cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val tmdbRepository: TMDBRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopRatedViewModel::class.java))
                return TopRatedViewModel(tmdbRepository) as T
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}