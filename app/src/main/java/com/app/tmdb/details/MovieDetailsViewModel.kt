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

//    val movieFavorite = tmdbRepository.getFavoriteStatus(id: Int) as LiveData


//    val movieFavorite: LiveData<Int>>
//        get() = tmd.allWords.flowOn(Dispatchers.Main)
//            .asLiveData(context = viewModelScope.coroutineContext)
//
//    val movieFavorite: Flow<Int> = t.getAllWords()
}
