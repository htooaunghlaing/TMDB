package com.app.tmdb.data.pojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "popular_movies")
data class PopularMovie(
    @PrimaryKey var id: Int,
    @SerializedName("original_title")
    var originalTitle: String,
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("original_language")
    var originalLanguage: String,
    var overview: String,
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String?,
    var title: String,
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int,
    var favorite: Int,
) : Serializable {
    object ModelMapper {
        fun from(movie: UpcomingMovie) =
            PopularMovie(
                movie.id,
                movie.originalTitle,
                movie.adult,
                movie.backdropPath,
                movie.genreIds,
                movie.originalLanguage,
                movie.overview,
                movie.popularity,
                movie.posterPath,
                movie.releaseDate,
                movie.title,
                movie.video,
                movie.voteAverage,
                movie.voteCount,
                movie.favorite
            )
    }
}
