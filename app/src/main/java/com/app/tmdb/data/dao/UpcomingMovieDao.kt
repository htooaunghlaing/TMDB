package com.app.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.tmdb.data.pojo.UpcomingMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface UpcomingMovieDao {

    @Query("SELECT * from upcoming_movies")
    fun getAllUpcomingMovies() : Flow<List<UpcomingMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(upcomingMovieList : List<UpcomingMovie>)

    @Query("DELETE from upcoming_movies")
    suspend fun deleteAllUpcomingMovies()
}