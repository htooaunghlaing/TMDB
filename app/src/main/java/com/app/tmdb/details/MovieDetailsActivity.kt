package com.app.tmdb.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.app.tmdb.R
import com.app.tmdb.data.pojo.Movie
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.databinding.ActivityMovieDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDetail = intent.getSerializableExtra("movie_object") as? PopularMovie


        if (movieDetail != null) {
            binding.apply {
                txtTitle.text = movieDetail.originalTitle
                txtOverView.text = movieDetail.overview

                Glide.with(imgPoster)
                    .load("https://image.tmdb.org/t/p/w342${movieDetail.posterPath}")
                    .transform(CenterCrop())
                    .into(imgPoster)

                if(movieDetail.favorite == 1){
                    btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                }else{
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                }

                val animation: Animation = AnimationUtils.loadAnimation(
                    this@MovieDetailsActivity, R.anim.bounce
                )

                btnFavorite.setOnClickListener {
                    btnFavorite.startAnimation(animation)
                    if (movieDetail.favorite == 0) {
                        Timber.d("NOT FAVORITE YET, Now FAVORITING")
                        viewModel.setFavorite(movieDetail.id)
                        movieDetail.favorite = 1
                        btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                    } else {
                        Timber.d("FAVORITED , Now UNFAVORITING")
                        viewModel.setUnFavorite(movieDetail.id)
                        movieDetail.favorite = 0
                        btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    }
                }

            }
        }

    }
}