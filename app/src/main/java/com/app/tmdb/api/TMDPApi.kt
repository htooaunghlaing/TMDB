package com.app.tmdb.api

import com.app.tmdb.data.pojo.MovieListResponse
import com.app.tmdb.data.pojo.UpcomingMovieListResponse
import retrofit2.http.GET

interface TMDPApi {

    @GET("popular?api_key=82c33b21d553a476fcf51e677931419c&language=en-US&page=1")
    suspend fun getPopularMovies() : MovieListResponse

    @GET("upcoming?api_key=82c33b21d553a476fcf51e677931419c&language=en-US&page=1")
    suspend fun getUpcomingMovies() : UpcomingMovieListResponse
}