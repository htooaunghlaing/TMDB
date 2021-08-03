package com.app.tmdb.details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.app.tmdb.R

import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.databinding.ActivityMovieDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra("movie_object") as? PopularMovie

        val favoriteBorder = ContextCompat.getDrawable(this@MovieDetailsActivity, com.app.tmdb.R.drawable.ic_favorite_border)

        binding.apply {
            if (movie != null) {
                txtTitle.text = movie.originalTitle
                txtOverView.text = movie.overview

                Glide.with(imgPoster)
                    .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                    .transform(CenterCrop())
                    .into(imgPoster)

                if(movie.favorite == 1){
                    btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                }else{
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                }

                btnFavorite.setOnClickListener {
                    if (movie.favorite == 0) {
                        Timber.d("NOT FAVORITE YET, Now FAVORITING")
                        viewModel.setFavorite(movie.id)
                        movie.favorite = 1
                        btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                    } else {
                        Timber.d("FAVORITED , Now UNFAVORITING")
                        viewModel.setUnFavorite(movie.id)
                        movie.favorite = 0
                        btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    }
                }
            }
        }
    }
}