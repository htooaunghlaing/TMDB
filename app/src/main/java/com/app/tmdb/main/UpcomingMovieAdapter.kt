package com.app.tmdb.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tmdb.data.pojo.UpcomingMovie

import com.app.tmdb.databinding.ItemMovieBinding
import com.app.tmdb.databinding.ItemUpcomingMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class UpcomingMovieAdapter constructor(private val upcomingMovieClickListener: UpcomingMovieClickListener) : ListAdapter<UpcomingMovie, UpcomingMovieAdapter.MovieViewHolder>(MovieComparator()) {

    class MovieViewHolder(private val itemMovieBinding: ItemUpcomingMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root){

        private var lastAnimatedPosition = -1

            fun bind(upcomingMovie: UpcomingMovie, upcomingMovieClickListener: UpcomingMovieClickListener, position: Int){
                itemMovieBinding.apply {

                    Glide.with(imgUpcomingMovie)
                        .load("https://image.tmdb.org/t/p/w342${upcomingMovie.posterPath}")
                        .transform(CenterCrop())
                        .into(imgUpcomingMovie)

                    txtMovieName.text = upcomingMovie.originalTitle

                    itemView.setOnClickListener{
                        upcomingMovieClickListener.onUpcomingMovieClick(upcomingMovie, imgUpcomingMovie)
                    }

                    val animation: Animation = AnimationUtils.loadAnimation(
                        imgUpcomingMovie.context,
                        if (position > lastAnimatedPosition){
                            com.app.tmdb.R.anim.up_from_bottom
                        } else {
                            com.app.tmdb.R.anim.down_from_top
                        }
                    )
                    itemView.startAnimation(animation)
                    lastAnimatedPosition = position

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if(movieItem != null){
            holder.bind(movieItem, upcomingMovieClickListener, position)
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<UpcomingMovie>(){

        override fun areItemsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie) =
            oldItem.originalTitle == newItem.originalTitle

        override fun areContentsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie) =
            oldItem == newItem

    }
}