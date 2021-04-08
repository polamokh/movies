package me.polamokh.movies.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import me.polamokh.movies.R
import me.polamokh.movies.adapter.LoadingStateAdapter
import me.polamokh.movies.databinding.FragmentSearchBinding
import me.polamokh.movies.network.TMDBApi
import me.polamokh.movies.repo.TMDBRepository
import me.polamokh.movies.ui.BaseFragment

class SearchFragment : BaseFragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, SearchViewModel.Factory(TMDBRepository(TMDBApi.service)))
                .get(SearchViewModel::class.java)

        super.handleUiStates()

        with(binding.searchRv) {
            this.adapter = movieAdapter.withLoadStateHeaderAndFooter(
                LoadingStateAdapter { movieAdapter.retry() },
                LoadingStateAdapter { movieAdapter.retry() })
        }

        binding.uiLoadingState.loadingErrorBtn.setOnClickListener { movieAdapter.retry() }

        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun handleUiErrorState(state: LoadState) {
        binding.uiLoadingState.loadingErrorImage.isVisible = state is LoadState.Error
        binding.uiLoadingState.loadingErrorText.isVisible = state is LoadState.Error
        binding.uiLoadingState.loadingErrorBtn.isVisible = state is LoadState.Error
    }

    override fun handleUiNotLoadingState(state: LoadState) {
        binding.searchRv.isVisible = state is LoadState.NotLoading
    }

    override fun handleUiLoadingState(state: LoadState) {
        binding.uiLoadingState.loadingProgress.isVisible = state is LoadState.Loading
    }

    override fun handleUiEmptyState(state: LoadState, append: LoadState) {
        if (state is LoadState.NotLoading &&
            append.endOfPaginationReached &&
            movieAdapter.itemCount < 1
        ) {
            binding.searchRv.isVisible = false
            binding.uiLoadingState.loadingEmpty.isVisible = true
        } else
            binding.uiLoadingState.loadingEmpty.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchItem.expandActionView()
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovies(query)
                    searchView.clearFocus()

                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun navigateToDetailsFragment(movieId: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movieId)
        )
    }
}