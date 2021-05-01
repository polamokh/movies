package me.polamokh.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.polamokh.movies.domain.DetailedMovie
import me.polamokh.movies.repo.TMDBRepository
import me.polamokh.movies.utils.UiState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val tmdbRepository: TMDBRepository) :
    ViewModel() {

    private val _movie = MutableLiveData<UiState<DetailedMovie>>()
    val movie: LiveData<UiState<DetailedMovie>>
        get() = _movie

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _movie.postValue(UiState.Loading)

                    val movie = tmdbRepository.getMovieDetails(movieId)
                    _movie.postValue(UiState.Success(movie))
                } catch (e: Exception) {
                    _movie.postValue(UiState.Failure(e))
                }
            }
        }
    }
}