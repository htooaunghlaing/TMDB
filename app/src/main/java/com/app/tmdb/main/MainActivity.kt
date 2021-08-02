package com.app.tmdb.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.tmdb.databinding.ActivityMainBinding
import com.app.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularMovieAdapter  = MovieAdapter()
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
}