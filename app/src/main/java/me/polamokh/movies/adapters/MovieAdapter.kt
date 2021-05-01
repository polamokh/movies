package me.polamokh.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.polamokh.movies.databinding.MovieItemBinding
import me.polamokh.movies.domain.Movie
import me.polamokh.movies.utils.OnItemClickListener

class MovieAdapter(private val onItemClickListener: OnItemClickListener<Int>) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(movieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener { onItemClickListener.clickListener(item.id) }
        }
    }

    class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val binding =
                    MovieItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MovieViewHolder(binding)
            }
        }
    }

    companion object {
        private val movieDiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.voteAverage == newItem.voteAverage &&
                        oldItem.imagePath == newItem.imagePath
            }
        }
    }
}