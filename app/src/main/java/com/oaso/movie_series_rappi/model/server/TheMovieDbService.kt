package com.oaso.movie_series_rappi.model.server

import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.model.server.models.movie.MovieDbResult
import com.oaso.movie_series_rappi.model.server.models.videos.MovieVideos
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TheMovieDbService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDbResult

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDbResult

    @GET
    suspend fun getVideos(
        @Url url: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): MovieVideos
}