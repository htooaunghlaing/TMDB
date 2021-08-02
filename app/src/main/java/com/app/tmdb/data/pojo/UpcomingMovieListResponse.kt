package com.app.tmdb.data.pojo

import com.google.gson.annotations.SerializedName

data class UpcomingMovieListResponse(
    val page: Int,
    @SerializedName("results")
    val upcomingMovieList: List<UpcomingMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)