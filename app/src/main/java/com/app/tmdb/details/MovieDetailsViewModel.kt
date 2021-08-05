package com.app.tmdb.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.tmdb.data.repo.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val tmdbRepository: TMDBRepository
) : ViewModel() {

    fun setFavorite(id: Int) = viewModelScope.launch {
        tmdbRepository.setFavoriteMovie(id)
    }

    fun setUnFavorite(id: Int) = viewModelScope.launch {
        tmdbRepository.setUnFavoriteMovie(id)
    }

    fun setUpcomingFavorite(id: Int) = viewModelScope.launch {
        tmdbRepository.setUpcomingFavoriteMovie(id)
    }

    fun setUpcomingUnFavorite(id: Int) = viewModelScope.launch {
        tmdbRepository.setUpcomingUnFavoriteMovie(id)
    }
}
