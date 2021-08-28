package com.app.tmdb.data.repo

import androidx.room.withTransaction
import com.app.tmdb.api.TMDPApi
import com.app.tmdb.app.TMDBApp
import com.app.tmdb.data.db.TMDBDatabase
import com.app.tmdb.util.networkBoundResource
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val tmdbAPI: TMDPApi,
    private val database: TMDBDatabase
) {
    private val popularMovieDao = database.popularMovieDao()
    private val upcomingMovieDao = database.upcomingMovieDao()

    @Inject
    lateinit var tmdbApp: TMDBApp

    fun getPopularMovies() = networkBoundResource(
        query = {
            popularMovieDao.getAllPopularMovies()
        },
        fetch = {
            tmdbAPI.getPopularMovies(com.app.tmdb.BuildConfig.TMDB_API_KEY, "en-US", 1)
        },
        saveFetchResult = { popularMovieList ->
            database.withTransaction {
                popularMovieDao.deleteAllPopularMovies()
                popularMovieDao.insertpopularMovie(popularMovieList.results)
            }
        }
    )


    fun getUpcomingMovies() = networkBoundResource(
        query = {
            upcomingMovieDao.getAllUpcomingMovies()
        },
        fetch = {
            tmdbAPI.getUpcomingMovies(com.app.tmdb.BuildConfig.TMDB_API_KEY, "en-US", 1)
        },
        saveFetchResult = { movelistResponse ->
            database.withTransaction {
                upcomingMovieDao.deleteAllUpcomingMovies()
                upcomingMovieDao.insertUpcomingMovie(movelistResponse.upcomingMovieList)
            }
        }
    )

    suspend fun setFavoriteMovie(id: Int) {
        popularMovieDao.setFavorite(id)
    }

    suspend fun setUnFavoriteMovie(id: Int) {
        popularMovieDao.setUnFavorite(id)
    }

    suspend fun setUpcomingFavoriteMovie(id: Int) {
        upcomingMovieDao.setFavorite(id)
    }

    suspend fun setUpcomingUnFavoriteMovie(id: Int) {
        upcomingMovieDao.setUnFavorite(id)
    }
}
