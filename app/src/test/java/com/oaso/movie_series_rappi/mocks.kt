package com.oaso.movie_series_rappi

import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.model.server.models.videos.Result
import com.oaso.movie_series_rappi.ui.popular.NavPopularMovie
import com.oaso.movie_series_rappi.ui.top_rated.NavRatedMovie

val mockPopularMovie = PopularMovie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false,
    1234
)

val mockNavPopularMovie = NavPopularMovie(
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false,
    1234
)

val mockNavRatedMovie = NavRatedMovie(
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false,
    1234
)


val mockRatedMovie = RatedMovie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    false,
    1234
)
