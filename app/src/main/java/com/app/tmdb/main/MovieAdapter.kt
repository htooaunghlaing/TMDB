package com.app.tmdb.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class MovieAdapter constructor(private val movieItemClickListener: MovieItemClickListener) :
    ListAdapter<PopularMovie, MovieAdapter.MovieViewHolder>(MovieComparator()) {

    class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bind(popularMovie: PopularMovie, movieItemClickListener: MovieItemClickListener) =
            with(itemView) {
                itemMovieBinding.apply {

                    Glide.with(imgMovie)
                        .load("https://image.tmdb.org/t/p/w342${popularMovie.posterPath}")
                        .transform(CenterCrop())
                        .into(imgMovie)

                    txtMovieName.text = popularMovie.originalTitle

                    itemView.setOnClickListener{
                        movieItemClickListener.onMovieClickListener(popularMovie, imgMovie)
                    }
                }
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem, movieItemClickListener)
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<PopularMovie>() {

        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem.originalTitle == newItem.originalTitle

        override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem == newItem

    }
}