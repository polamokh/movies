package me.polamokh.movies.ui.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.polamokh.movies.repo.TMDBRepository
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(tmdbRepository: TMDBRepository) : ViewModel() {

    val movies = tmdbRepository.getNowPlayingMovies().cachedIn(viewModelScope)
}