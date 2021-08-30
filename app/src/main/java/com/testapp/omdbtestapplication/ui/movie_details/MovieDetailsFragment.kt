package com.testapp.omdbtestapplication.ui.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.testapp.omdbtestapplication.R
import com.testapp.omdbtestapplication.data.OmdbMovieDetailResponse
import com.testapp.omdbtestapplication.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieDetails(args.movieId)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, {
            bindMovieDetails(it)
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            if (it) binding.progressBar.visibility =
                View.VISIBLE else binding.progressBar.visibility = View.GONE
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovieDetails(it: OmdbMovieDetailResponse) {
        binding.ivPoster.load(it.poster) { placeholder(R.drawable.ic_broken_image) }
        binding.tvMovieName.text = it.title + " (${it.releaseYear})"
        binding.tvMetacritic.text = it.metascore
        binding.tvImdbRating.text = it.imdbRating
        binding.tvRuntime.text = it.runtime
        binding.tvRate.text = it.ratingSystem
        binding.tvMovieGenre.text = it.genre
        binding.tvDirector.text = it.director
        binding.tvWriters.text = it.writer
        binding.tvActors.text = it.actors
        binding.tvReleaseDate.text = it.releaseDate
        binding.tvPlot.text = it.plot
    }
}