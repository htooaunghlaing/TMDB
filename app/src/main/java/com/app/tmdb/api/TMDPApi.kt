package com.app.tmdb.api

import com.app.tmdb.data.pojo.MovieListResponse
import com.app.tmdb.data.pojo.UpcomingMovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDPApi {

    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String, @Query("language") language: String, @Query("page") page: Int) : MovieListResponse

    @GET("upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") api_key: String, @Query("language") language: String, @Query("page") page: Int) : UpcomingMovieListResponse
}