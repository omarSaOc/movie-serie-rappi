package com.oaso.movie_series_rappi.model.server


class MoviesRepository(private val service: TheMovieDbService) {

    suspend fun getPopularMovies(apiKey: String) =
        service.getPopularMovies(apiKey)

//    suspend fun getTopRated(apiKey: String) =
//        theMovieDbService.getTopRatedMovies(apiKey)
}