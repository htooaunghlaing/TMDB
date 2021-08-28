package com.app.tmdb.data.pojo

data class MovieListResponse(
    val page: Int,

    val results: List<PopularMovie>,

    val total_pages: Int,

    val totalResults: Int
)