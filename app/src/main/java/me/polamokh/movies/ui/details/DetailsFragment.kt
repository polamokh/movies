package me.polamokh.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.movies.databinding.FragmentDetailsBinding
import me.polamokh.movies.domain.DetailedMovie
import me.polamokh.movies.utils.UiState

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getMovieDetails(args.movieId)

            binding.uiLoadingState.loadingErrorBtn.setOnClickListener {
                viewModel.getMovieDetails(args.movieId)
            }
        }

        viewModel.movie.observe(viewLifecycleOwner, {
            it?.let {
                handleLoadingState(it)
                handleSuccessState(it)
                handleFailureState(it)
            }
        })
    }

    private fun handleLoadingState(state: UiState<DetailedMovie>) {
        binding.uiLoadingState.loadingProgress.isVisible = state is UiState.Loading
    }

    private fun handleSuccessState(state: UiState<DetailedMovie>) {
        binding.movieDetails.isVisible = state is UiState.Success
        binding.movie = state.dataIfAvailable
        requireActivity().title = state.dataIfAvailable?.title
    }

    private fun handleFailureState(state: UiState<DetailedMovie>) {
        binding.uiLoadingState.loadingErrorImage.isVisible = state is UiState.Failure
        binding.uiLoadingState.loadingErrorText.isVisible = state is UiState.Failure
        binding.uiLoadingState.loadingErrorBtn.isVisible = state is UiState.Failure
    }
}