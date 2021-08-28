package com.app.tmdb.details

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.app.tmdb.R
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.databinding.ActivityMovieDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import android.view.ViewAnimationUtils
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.app.tmdb.util.Utality
import javax.inject.Inject
import kotlin.math.hypot
import kotlin.math.max


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    @Inject lateinit var utality: Utality

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDetail = intent.getSerializableExtra("movie_object") as PopularMovie
        val movieType = intent.getSerializableExtra("movie_type") as String


        binding.apply {

            //change statusbar color
            utality.setStatusBarColor(this@MovieDetailsActivity, ContextCompat.getColor(applicationContext,
                R.color.colorPrimaryDark))

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
                    if(movieType.equals("popular")){
                        viewModel.setFavorite(movieDetail.id)
                    }else{
                        viewModel.setUpcomingFavorite(movieDetail.id)
                    }
                    viewModel.setFavorite(movieDetail.id)
                    movieDetail.favorite = 1
                    btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                } else {
                    Timber.d("FAVORITED , Now UNFAVORITING")
                    if(movieType.equals("popular")){
                        viewModel.setUnFavorite(movieDetail.id)
                    }else{
                        viewModel.setUpcomingUnFavorite(movieDetail.id)
                    }
                    movieDetail.favorite = 0
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }


            if (backgroundContainer.isAttachedToWindow) {
                backgroundContainer.post {
                    val x = intent.getIntExtra("x", 0)
                    val y = intent.getIntExtra("y", 0)
                    val animator: Animator = createRevealAnimator(false, x, y)
                    animator.start()
                    backgroundContainer.visibility = View.VISIBLE
                }
            }

            imageButton3.setOnClickListener{
                onBackPressed()
            }

        }
    }

    private fun getDips(dps: Int): Int {
        val resources: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dps.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun onBackPressed() {
        val cx: Int = binding.backgroundContainer.width - getDips(44)
        val cy: Int = binding.backgroundContainer.bottom - getDips(44)
        val finalRadius: Int = max(binding.backgroundContainer.width, binding.backgroundContainer.height)
        val circularReveal: Animator =
            ViewAnimationUtils.createCircularReveal(binding.backgroundContainer, cx, cy, finalRadius.toFloat(), 0f)
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator?) {}
            override fun onAnimationEnd(animator: Animator?) {
                binding.backgroundContainer.visibility = View.INVISIBLE
                finish()
            }

            override fun onAnimationCancel(animator: Animator?) {}
            override fun onAnimationRepeat(animator: Animator?) {}
        })
        circularReveal.duration = 400
        circularReveal.start()
    }

    private fun createRevealAnimator(reversed: Boolean, x: Int, y: Int): Animator {
        val hypot = hypot(binding.backgroundContainer.height.toDouble(),
            binding.backgroundContainer.width.toDouble()
        ).toFloat()
        val startRadius: Float = if (reversed) hypot else 0F
        val endRadius: Float = if (reversed) 0F else hypot
        val animator = ViewAnimationUtils.createCircularReveal(
            binding.backgroundContainer, x, y,
            startRadius,
            endRadius
        )
        animator.duration = 800
        animator.interpolator = AccelerateDecelerateInterpolator()
        if (reversed) animator.addListener(animatorListener)
        return animator
    }

    private val animatorListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            binding.backgroundContainer.visibility = View.INVISIBLE
            finish()
        }

        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    }
}