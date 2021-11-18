package com.oaso.movie_series_rappi.model.server

import com.oaso.movie_series_rappi.model.server.MovieDbResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ) : MovieDbResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ) : MovieDbResult
}