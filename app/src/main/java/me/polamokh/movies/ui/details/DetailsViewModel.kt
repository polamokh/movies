package me.polamokh.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import me.polamokh.movies.domain.DetailedMovie
import me.polamokh.movies.network.TMDBApi
import me.polamokh.movies.utils.UiState

class DetailsViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _movie = MutableLiveData<UiState<DetailedMovie>>()
    val movie: LiveData<UiState<DetailedMovie>>
        get() = _movie

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _movie.postValue(UiState.Loading)

                    val movie = TMDBApi.service.getDetailsAsync(movieId).await()
                    _movie.postValue(UiState.Success(movie))
                } catch (e: Exception) {
                    _movie.postValue(UiState.Failure(e))
                }
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}