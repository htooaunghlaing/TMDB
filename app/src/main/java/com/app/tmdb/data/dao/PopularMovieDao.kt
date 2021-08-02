package com.app.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.app.tmdb.data.pojo.PopularMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMovieDao {

    @Query("SELECT * from popular_movies")
    fun getAllPopularMovies() : Flow<List<PopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertpopularMovie(popularMovieList : List<PopularMovie>)

    @Query("DELETE from popular_movies")
    suspend fun deleteAllPopularMovies()
}