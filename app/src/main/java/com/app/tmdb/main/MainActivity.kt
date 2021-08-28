package com.app.tmdb.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tmdb.R
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.data.pojo.UpcomingMovie
import com.app.tmdb.databinding.ActivityMainBinding
import com.app.tmdb.details.MovieDetailsActivity
import com.app.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import com.app.tmdb.util.ConnectionLiveData
import com.app.tmdb.util.Utality
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieItemClickListener,
    UpcomingMovieClickListener {

    private val viewModel : MovieListViewModel by viewModels()

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    @Inject
    lateinit var utality: Utality

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularMovieAdapter  = MovieAdapter(this@MainActivity)
        val upcomingMovieAdapter  = UpcomingMovieAdapter(this@MainActivity)

        binding.apply {

            //change statusbar color
            utality.setStatusBarColor(this@MainActivity, ContextCompat.getColor(applicationContext,
                R.color.colorPrimaryDark))

            popularRecycler.apply {
                adapter = popularMovieAdapter
                layoutManager = LinearLayoutManager(this@MainActivity ,LinearLayoutManager.HORIZONTAL ,false)
                isNestedScrollingEnabled = false
                popularRecycler.setHasFixedSize(true)
            }

            upcomingRecycler.apply {
                adapter = upcomingMovieAdapter
                layoutManager = LinearLayoutManager(this@MainActivity ,LinearLayoutManager.HORIZONTAL ,false)
                isNestedScrollingEnabled = false
                popularRecycler.setHasFixedSize(true)
            }

            //observe popular list
            viewModel.popularMovieList.observe(this@MainActivity) { result ->

                popularRecycler.visibility = View.VISIBLE
                popularMovieAdapter.submitList(result.data)


                pBarPopular.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                txtErrPopular.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                txtErrPopular.text = result.error?.localizedMessage

                Timber.e(result.error?.localizedMessage)
            }

            //observe upcoming list
            viewModel.upcomingMovieList.observe(this@MainActivity) { result ->

                upcomingRecycler.visibility = View.VISIBLE
                upcomingMovieAdapter.submitList(result.data)


                pBarUpcoming.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                txtErrUpcoming.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                txtErrUpcoming.text = result.error?.localizedMessage
            }


//            connectionLiveData.observe(this@MainActivity, {
//                //TODO network observe live
//                if (it)
//                //Toast.makeText(this@MainActivity, "Internect Active",Toast.LENGTH_LONG).show()
//                else
//                //Toast.makeText(this@MainActivity, "You are offline!",Toast.LENGTH_LONG).show()
//            })

        }

    }

    override fun onMovieClickListener(item: PopularMovie,imageView: ImageView) {
        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
        intent.putExtra("movie_object", item)
        intent.putExtra("movie_type", "popular")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, resources.getString(R.string.app_name))
        startActivity(intent, options.toBundle())
    }

    override fun onUpcomingMovieClick(item: UpcomingMovie, imageView: ImageView) {
        val movie = PopularMovie.ModelMapper.from(item)

        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
        intent.putExtra("movie_object", movie)
        intent.putExtra("movie_type", "upcoming")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, resources.getString(R.string.app_name))
        startActivity(intent, options.toBundle())
    }
}


interface MovieItemClickListener {
    fun onMovieClickListener(item: PopularMovie, imageView: ImageView)
}

interface UpcomingMovieClickListener {
    fun onUpcomingMovieClick(item: UpcomingMovie, imageView: ImageView)
}