package com.app.tmdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.tmdb.data.dao.PopularMovieDao
import com.app.tmdb.data.dao.UpcomingMovieDao
import com.app.tmdb.data.pojo.PopularMovie
import com.app.tmdb.data.pojo.UpcomingMovie
import com.app.tmdb.util.Converters

@Database(entities = [PopularMovie::class, UpcomingMovie::class], version = 1)
@TypeConverters(Converters::class)
abstract class TMDBDatabase : RoomDatabase() {

    abstract fun popularMovieDao() : PopularMovieDao
    abstract fun upcomingMovieDao() : UpcomingMovieDao
}