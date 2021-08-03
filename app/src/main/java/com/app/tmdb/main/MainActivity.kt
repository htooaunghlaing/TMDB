package com.app.tmdb.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tmdb.R
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.databinding.ActivityMainBinding
import com.app.tmdb.details.MovieDetailsActivity
import com.app.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieItemClickListener {

    private val viewModel : MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularMovieAdapter  = MovieAdapter(this@MainActivity)
        val upcomingMovieAdapter  = UpcomingMovieAdapter()

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

                pBarPopular.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                txtErrPopular.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                txtErrPopular.text = result.error?.localizedMessage
            }

            //observe upcoming list
            viewModel.upcomingMovieList.observe(this@MainActivity) { result ->
                upcomingMovieAdapter.submitList(result.data)

                pBarUpcoming.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                txtErrUpcoming.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                txtErrUpcoming.text = result.error?.localizedMessage
            }

        }

    }

    override fun onMovieClickListener(item: PopularMovie,imageView: ImageView) {
//        val intent = Intent(this, MovieDetailsActivity::class.java)
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "transition_name")
//        startActivity(intent, options.toBundle())

        val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
        intent.putExtra("movie_object", item)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, resources.getString(R.string.app_name))
        startActivity(intent, options.toBundle())
    }
}


interface MovieItemClickListener {
    fun onMovieClickListener(item: PopularMovie, imageView: ImageView)
}