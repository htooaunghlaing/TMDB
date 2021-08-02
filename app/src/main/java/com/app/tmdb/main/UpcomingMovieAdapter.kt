package com.app.tmdb.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tmdb.data.pojo.UpcomingMovie

import com.app.tmdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class UpcomingMovieAdapter : ListAdapter<UpcomingMovie, UpcomingMovieAdapter.MovieViewHolder>(MovieComparator()) {

    class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root){

            fun bind(upcomingMovie: UpcomingMovie){
                itemMovieBinding.apply {

                    Glide.with(imgMovie)
                        .load("https://image.tmdb.org/t/p/w342${upcomingMovie.posterPath}")
                        .transform(CenterCrop())
                        .into(imgMovie)

                    txtMovieName.text = upcomingMovie.originalTitle

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if(movieItem != null){
            holder.bind(movieItem)
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<UpcomingMovie>(){

        override fun areItemsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie) =
            oldItem.originalTitle == newItem.originalTitle

        override fun areContentsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie) =
            oldItem == newItem

    }
}