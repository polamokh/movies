package me.polamokh.movies.utils

class OnItemClickListener<T>(val clickListener: (movieId: T) -> Unit)