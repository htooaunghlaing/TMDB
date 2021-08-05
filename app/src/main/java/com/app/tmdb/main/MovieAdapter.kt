package com.app.tmdb.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.tmdb.data.pojo.PopularMovie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.tmdb.databinding.ItemMovieBinding
import android.util.DisplayMetrics
import android.view.WindowManager
import com.app.tmdb.R


class MovieAdapter constructor(private val movieItemClickListener: MovieItemClickListener) :
    ListAdapter<PopularMovie, MovieAdapter.MovieViewHolder>(MovieComparator()) {



    class MovieViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        private var lastAnimatedPosition = -1

        fun bind(popularMovie: PopularMovie, movieItemClickListener: MovieItemClickListener, position: Int) {
            itemMovieBinding.apply {

                Glide.with(imgMovie)
                    .load("https://image.tmdb.org/t/p/w342${popularMovie.posterPath}")
                    .transform(CenterCrop())
                    .into(imgMovie)

                txtMovieName.text = popularMovie.originalTitle

                itemView.setOnClickListener{
                    movieItemClickListener.onMovieClickListener(popularMovie, imgMovie)
                }

                val animation: Animation = AnimationUtils.loadAnimation(
                    imgMovie.context,
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
//        val proportionalHeight: Int = ((getDeviceWidth(holder.itemView.context) / 2 * 1.9f).toInt())
//        val params: TableRow.LayoutParams =
//            TableRow.LayoutParams(
//                TableRow.LayoutParams.MATCH_PARENT,
//                proportionalHeight
//            ) // (width, height)
//
//        holder.itemView.layoutParams = params
    }


    class MovieComparator : DiffUtil.ItemCallback<PopularMovie>() {

        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem.originalTitle == newItem.originalTitle

        override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie) =
            oldItem == newItem

    }

    fun getDeviceWidth(context: Context): Int {
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics.widthPixels
    }
}