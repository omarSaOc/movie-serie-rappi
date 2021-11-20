package com.oaso.movie_series_rappi.model.database.rated_movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatedMovieDao {
    @Query("SELECT * FROM RatedMovie")
    fun getAll(): List<RatedMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<RatedMovie>)

    @Query("SELECT *  FROM RatedMovie WHERE id = :id")
    fun getMovieById(id: Int): RatedMovie

    @Query("SELECT COUNT(id) FROM RatedMovie ")
    fun movieCount() : Int
}