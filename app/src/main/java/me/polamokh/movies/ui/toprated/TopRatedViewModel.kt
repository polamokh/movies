package me.polamokh.movies.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.polamokh.movies.repo.TMDBRepository
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(tmdbRepository: TMDBRepository) : ViewModel() {

    val movies = tmdbRepository.getTopRatedMovies().cachedIn(viewModelScope)
}