package com.app.tmdb.data.pojo

data class MovieListResponse(
    val page: Int,
//    @SerializedName("results")
    val results: List<PopularMovie>,
//    @SerializedName("total_pages")
    val total_pages: Int,
//    @SerializedName("total_results")
    val totalResults: Int
)