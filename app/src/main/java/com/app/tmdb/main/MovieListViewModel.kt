package com.app.tmdb.main

import androidx.lifecycle.*

import com.app.tmdb.data.repo.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    tmdbRepository: TMDBRepository
) : ViewModel() {

    val popularMovieList = tmdbRepository.getPopularMovies().asLiveData()

    val upcomingMovieList = tmdbRepository.getUpcomingMovies().asLiveData()
}