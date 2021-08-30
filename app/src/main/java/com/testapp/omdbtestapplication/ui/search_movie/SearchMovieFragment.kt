package com.testapp.omdbtestapplication.ui.search_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.testapp.omdbtestapplication.databinding.FragmentMovieSearchBinding
import com.testapp.omdbtestapplication.ui.search_movie.adapter.SearchMovieAdapter
import com.testapp.omdbtestapplication.ui.search_movie.adapter.SearchMovieAdapterCallback
import com.testapp.omdbtestapplication.extensions.hide
import com.testapp.omdbtestapplication.extensions.hideKeyboard
import com.testapp.omdbtestapplication.extensions.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchMovieFragment : Fragment(), SearchMovieAdapterCallback {

    private lateinit var binding: FragmentMovieSearchBinding
    private lateinit var searchMovieAdapter: SearchMovieAdapter

    private val viewModel: SearchMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeLiveData()

        binding.fabSearch.setOnClickListener {
            viewModel.search(binding.etSearch.text.toString())
            hideKeyboard()
        }

        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(binding.etSearch.text.toString())
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onMovieClicked(movieId: String) {
        findNavController().navigate(
            SearchMovieFragmentDirections.actionSearchMoviesFragmentToMovieDetailsFragment(movieId)
        )
    }

    override fun onWatchLaterClicked(movieId: String, isAddedToWatchLater: Boolean) {
        viewModel.updateWatchLater(movieId, isAddedToWatchLater)
    }

    override fun onWatchedClicked(movieId: String, isAddedToWatched: Boolean) {
        viewModel.updateWatched(movieId, isAddedToWatched)
    }

    private fun initRecyclerView() {
        binding.rvMovies.apply {
            searchMovieAdapter = SearchMovieAdapter(this@SearchMovieFragment)
            adapter = searchMovieAdapter
            itemAnimator = null
        }
    }

    private fun observeLiveData() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, {
            searchMovieAdapter.submitList(it)
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            if (it) binding.progressBar.show() else binding.progressBar.hide()
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}