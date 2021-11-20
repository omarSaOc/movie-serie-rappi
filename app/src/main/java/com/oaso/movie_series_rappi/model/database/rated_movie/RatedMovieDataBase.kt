package com.oaso.movie_series_rappi.model.database.rated_movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RatedMovie::class], version = 1)
abstract class RatedMovieDataBase : RoomDatabase() {
    abstract fun ratedMovieDao() : RatedMovieDao
}