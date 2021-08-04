package com.app.tmdb.data.pojo

import com.google.gson.annotations.SerializedName

open class Movie(
    var id: Int,
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genre_ids")
    open var genreIds: List<Int>,
    @SerializedName("original_language")
    open var originalLanguage: String,
    @SerializedName("original_title")
    open var originalTitle: String,
    open var overview: String,
    open var popularity: Double,
    @SerializedName("poster_path")
    open var posterPath: String,
    @SerializedName("release_date")
    open var releaseDate: String?,
    open var title: String,
    open var video: Boolean,
    @SerializedName("vote_average")
    open var voteAverage: Double,
    @SerializedName("vote_count")
    open var voteCount: Int,
    open var favorite: Int,
)