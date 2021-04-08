package me.polamokh.movies.utils

sealed class UiState<out R> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val exception: Exception) : UiState<Nothing>()

    val dataIfAvailable: R?
        get() = when (this) {
            is Loading -> null
            is Success -> data
            is Failure -> null
        }
}
