package me.polamokh.movies.ui

import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import me.polamokh.movies.adapter.MovieAdapter
import me.polamokh.movies.adapter.OnItemClickListener

abstract class BaseFragment : Fragment() {

    protected val movieAdapter = initializeMoviesAdapter()


    private fun initializeMoviesAdapter(): MovieAdapter {
        return MovieAdapter(OnItemClickListener {
            navigateToDetailsFragment(it)
        })
    }

    abstract fun navigateToDetailsFragment(movieId: Int)

    fun handleUiStates() {
        movieAdapter.addLoadStateListener { combinedLoadStates ->
            val state = combinedLoadStates.source.refresh
            handleUiLoadingState(state)
            handleUiNotLoadingState(state)
            handleUiErrorState(state)
            handleUiEmptyState(state, combinedLoadStates.append)
        }
    }

    abstract fun handleUiEmptyState(state: LoadState, append: LoadState)

    abstract fun handleUiErrorState(state: LoadState)

    abstract fun handleUiNotLoadingState(state: LoadState)

    abstract fun handleUiLoadingState(state: LoadState)
}