package com.app.tmdb.data.repo

import androidx.room.withTransaction
import com.app.tmdb.api.TMDPApi
import com.app.tmdb.data.db.TMDBDatabase
import com.app.tmdb.util.networkBoundResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val tmdbAPI : TMDPApi,
    private val database: TMDBDatabase
) {
    private val popularMovieDao = database.popularMovieDao()
    private val upcomingMovieDao = database.upcomingMovieDao()

    fun getPopularMovies() = networkBoundResource(
        query = {
            popularMovieDao.getAllPopularMovies()
        },
        fetch = {
            tmdbAPI.getPopularMovies()
        },
        saveFetchResult = { popularMovieList ->
            database.withTransaction {
                popularMovieDao.deleteAllPopularMovies()
                popularMovieDao.insertpopularMovie(popularMovieList.popularMovieList)
            }
        }
    )



    fun getUpcomingMovies() = networkBoundResource(
        query = {
            upcomingMovieDao.getAllUpcomingMovies()
        },
        fetch = {
            tmdbAPI.getUpcomingMovies()
        },
        saveFetchResult = { movelistResponse ->
            database.withTransaction {
                upcomingMovieDao.deleteAllUpcomingMovies()
                upcomingMovieDao.insertUpcomingMovie(movelistResponse.upcomingMovieList)
            }
        }
    )

    suspend fun setFavoriteMovie(id: Int){
        //GlobalScope.launch {
            popularMovieDao.setFavorite(id)
        //}
    }

    suspend fun setUnFavoriteMovie(id: Int){
        //GlobalScope.launch {
            popularMovieDao.setUnFavorite(id)
        //}
    }
}
