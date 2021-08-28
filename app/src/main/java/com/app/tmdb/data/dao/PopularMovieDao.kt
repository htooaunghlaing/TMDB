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

    @Query("UPDATE popular_movies SET favorite =  1 where id = :movieID")
    suspend fun setFavorite(movieID: Int)

    @Query("UPDATE popular_movies SET favorite = 0 where id = :movieID")
    suspend fun setUnFavorite(movieID: Int)

    @Query("SELECT favorite from popular_movies where id = :movieID")
    fun getFavorite(movieID: Int) : Flow<Int>
}