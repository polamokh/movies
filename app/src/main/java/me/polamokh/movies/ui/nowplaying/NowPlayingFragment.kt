package me.polamokh.movies.ui.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import me.polamokh.movies.adapter.LoadingStateAdapter
import me.polamokh.movies.databinding.FragmentNowPlayingBinding
import me.polamokh.movies.network.TMDBApi
import me.polamokh.movies.repo.TMDBRepository
import me.polamokh.movies.ui.BaseFragment

class NowPlayingFragment : BaseFragment() {

    private lateinit var viewModel: NowPlayingViewModel
    private lateinit var binding: FragmentNowPlayingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProvider(this, NowPlayingViewModel.Factory(TMDBRepository(TMDBApi.service)))
                .get(NowPlayingViewModel::class.java)

        super.handleUiStates()

        with(binding.nowPlayingRv) {
            this.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                LoadingStateAdapter { movieAdapter.retry() },
                LoadingStateAdapter { movieAdapter.retry() })
        }
        binding.uiLoadingState.loadingErrorBtn.setOnClickListener { movieAdapter.retry() }

        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun handleUiErrorState(state: LoadState) {
        binding.uiLoadingState.loadingErrorImage.isVisible = state is LoadState.Error
        binding.uiLoadingState.loadingErrorText.isVisible = state is LoadState.Error
        binding.uiLoadingState.loadingErrorBtn.isVisible = state is LoadState.Error
    }

    override fun handleUiNotLoadingState(state: LoadState) {
        binding.nowPlayingRv.isVisible = state is LoadState.NotLoading
    }

    override fun handleUiLoadingState(state: LoadState) {
        binding.uiLoadingState.loadingProgress.isVisible = state is LoadState.Loading
    }

    override fun handleUiEmptyState(state: LoadState, append: LoadState) {
        if (state is LoadState.NotLoading &&
            append.endOfPaginationReached &&
            movieAdapter.itemCount < 1
        ) {
            binding.nowPlayingRv.isVisible = false
            binding.uiLoadingState.loadingEmpty.isVisible = true
        } else
            binding.uiLoadingState.loadingEmpty.isVisible = false
    }

    override fun navigateToDetailsFragment(movieId: Int) {
        findNavController().navigate(
            NowPlayingFragmentDirections.actionNowPlayingFragmentToDetailsFragment(movieId)
        )
    }
}