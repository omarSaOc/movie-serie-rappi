package com.oaso.movie_series_rappi.model.database.popular_movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PopularMovie::class], version = 1)
abstract class PopularMovieDataBase : RoomDatabase() {
    abstract fun movieDao() : PopularMovieDao
}