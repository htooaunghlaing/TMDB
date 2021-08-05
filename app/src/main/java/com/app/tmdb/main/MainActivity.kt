package com.app.tmdb.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
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
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView








@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieItemClickListener,
    UpcomingMovieClickListener {

    private val viewModel : MovieListViewModel by viewModels()

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularMovieAdapter  = MovieAdapter(this@MainActivity)
        val upcomingMovieAdapter  = UpcomingMovieAdapter(this@MainActivity)

        binding.apply {
            popularRecycler.apply {
                adapter = popularMovieAdapter
                layoutManager = LinearLayoutManager(this@MainActivity ,LinearLayoutManager.HORIZONTAL ,false)
                popularRecycler.setHasFixedSize(true)
            }

            upcomingRecycler.apply {
                adapter = upcomingMovieAdapter
                layoutManager = LinearLayoutManager(this@MainActivity ,LinearLayoutManager.HORIZONTAL ,false)
            }

            //observe popular list
            viewModel.popularMovieList.observe(this@MainActivity) { result ->
                popularMovieAdapter.submitList(result.data)

                popularRecycler.isVisible = true
                pBarPopular.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                txtErrPopular.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                txtErrPopular.text = result.error?.localizedMessage

                Timber.e(result.error?.localizedMessage)
            }

            //observe upcoming list
            viewModel.upcomingMovieList.observe(this@MainActivity) { result ->
                upcomingMovieAdapter.submitList(result.data)

                upcomingRecycler.isVisible = true
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