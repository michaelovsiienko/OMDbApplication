package com.testapp.omdbtestapplication.ui.search_movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.testapp.omdbtestapplication.R
import com.testapp.omdbtestapplication.data.OmdbMovieResponse
import com.testapp.omdbtestapplication.databinding.ItemMovieBinding
import com.google.android.material.button.MaterialButton

class SearchMovieAdapter(
    private val callback: SearchMovieAdapterCallback
) : RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>() {

    private var items: List<OmdbMovieResponse> = ArrayList()

    class MovieItemDiffCallback(
        var oldMoviesList: List<OmdbMovieResponse>,
        var newMoviesList: List<OmdbMovieResponse>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldMoviesList.size
        }

        override fun getNewListSize(): Int {
            return newMoviesList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldMoviesList[oldItemPosition].imdbId == newMoviesList[newItemPosition].imdbId)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMoviesList[oldItemPosition] == newMoviesList[newItemPosition]
        }
    }

    fun submitList(moviesList: List<OmdbMovieResponse>) {
        val oldMoviesList = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            MovieItemDiffCallback(oldMoviesList, moviesList)
        )
        items = moviesList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class MovieViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val callback: SearchMovieAdapterCallback
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var movie: OmdbMovieResponse

        @SuppressLint("SetTextI18n")
        fun bind(item: OmdbMovieResponse) {
            this.movie = item
            itemBinding.ivPoster.load(movie.poster) { placeholder(R.drawable.ic_broken_image) }
            itemBinding.ivPoster.setOnClickListener { callback.onMovieClicked(movie.imdbId) }

            itemBinding.btnWatchLater.run {
                if (item.isAddedToWatchLater) {
                    this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorAccent)
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
                    this.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }

            itemBinding.btnWatchLater.setOnClickListener {
                callback.onWatchLaterClicked(movie.imdbId, !movie.isAddedToWatchLater)
                movie.isAddedToWatchLater = !movie.isAddedToWatchLater
                notifyItemChanged(adapterPosition)
            }

            itemBinding.btnWatched.run {
                if (item.isAddedToWatched) {
                    this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorAccent)
                    this.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    this.backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
                    this.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }

            itemBinding.btnWatched.setOnClickListener {
                callback.onWatchedClicked(movie.imdbId, !movie.isAddedToWatched)
                movie.isAddedToWatched = !movie.isAddedToWatched
                notifyItemChanged(adapterPosition)
            }
        }
    }
}