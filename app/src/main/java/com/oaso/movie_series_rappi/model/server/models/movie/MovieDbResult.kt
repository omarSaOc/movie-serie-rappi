package com.oaso.movie_series_rappi.model.server.models.movie

data class MovieDbResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)