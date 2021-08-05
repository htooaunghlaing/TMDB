package com.app.tmdb.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.tmdb.R
import com.app.tmdb.data.pojo.UpcomingMovie
import com.app.tmdb.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class UpcomingMovieAdapter constructor(private val upcomingMovieClickListener: UpcomingMovieClickListener) : ListAdapter<UpcomingMovie, UpcomingMovieAdapter.MovieViewHolder>(MovieComparator()) {

    class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root){

        private var lastAnimatedPosition = -1

            fun bind(upcomingMovie: UpcomingMovie, upcomingMovieClickListener: UpcomingMovieClickListener, position: Int){
                itemMovieBinding.apply {

                    val circularProgress = CircularProgressDrawable(imgMovie.context)
                    circularProgress.strokeWidth = 5f
                    circularProgress.centerRadius = 30f
                    circularProgress.start()

                    Glide.with(imgMovie)
                        .load("https://image.tmdb.org/t/p/w342${upcomingMovie.posterPath}")
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(withCrossFade())
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(circularProgress)
                        .into(imgMovie)

                    txtMovieName.text = upcomingMovie.originalTitle

                    itemView.setOnClickListener{
                        upcomingMovieClickListener.onUpcomingMovieClick(upcomingMovie, imgMovie)
                    }

                    val animation: Animation = AnimationUtils.loadAnimation(
                        imgMovie.context,
                        if (position > lastAnimatedPosition){
                            R.anim.up_from_bottom
                        } else {
                            R.anim.down_from_top
                        }
                    )
                    itemView.startAnimation(animation)
                    lastAnimatedPosition = position

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