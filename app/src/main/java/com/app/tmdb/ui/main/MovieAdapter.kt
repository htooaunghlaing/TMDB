package com.app.tmdb.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tmdb.data.pojo.PopularMovie
import com.bumptech.glide.Glide
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.tmdb.databinding.ItemMovieBinding
import com.app.tmdb.R
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class MovieAdapter constructor(private val movieItemClickListener: MovieItemClickListener) :
    ListAdapter<PopularMovie, MovieAdapter.MovieViewHolder>(MovieComparator()) {



    class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        private var lastAnimatedPosition = -1

        fun bind(popularMovie: PopularMovie, movieItemClickListener: MovieItemClickListener, position: Int) {
            itemMovieBinding.apply {

                val circularProgress = CircularProgressDrawable(imgMovie.context)
                circularProgress.strokeWidth = 5f
                circularProgress.centerRadius = 30f
                circularProgress.start()

                Glide.with(imgMovie)
                    .load("https://image.tmdb.org/t/p/w342${popularMovie.posterPath}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(circularProgress)
                    .into(imgMovie)
                txtMovieName.text = popularMovie.originalTitle

                itemView.setOnClickListener{
                    movieItemClickListener.onMovieClickListener(popularMovie, imgMovie)
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
        //parent.layoutParams.height =  ((getDeviceWidth(parent.context) / 2 * 1.9f).toInt())
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null) {
            holder.bind(movieItem, movieItemClickListener,position)
        }
    }


    class MovieComparator : DiffUtil.ItemCallback<PopularMovie>() {

        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem.originalTitle == newItem.originalTitle

        override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem == newItem

    }
}