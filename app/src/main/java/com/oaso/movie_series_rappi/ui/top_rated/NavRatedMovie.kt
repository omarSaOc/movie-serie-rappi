package com.oaso.movie_series_rappi.ui.top_rated

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavRatedMovie(
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val favorite: Boolean,
    val movieId: Int
): Parcelable
