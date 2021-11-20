package com.oaso.movie_series_rappi.model.database.popular_movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PopularMovieDao {

    @Query("SELECT * FROM PopularMovie")
    fun getAll(): List<PopularMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(popularMovies: List<PopularMovie>)

    @Query("SELECT *  FROM PopularMovie WHERE id = :id")
    fun getMovieById(id: Int): PopularMovie

    @Query("SELECT COUNT(id) FROM PopularMovie ")
    fun movieCount() : Int



}