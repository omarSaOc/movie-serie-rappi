package com.oaso.movie_series_rappi.model.server

import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovieDataBase
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovieDataBase
import com.oaso.movie_series_rappi.model.server.models.videos.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.oaso.movie_series_rappi.model.server.models.movie.Movie as ServerMovie

class MoviesRepository @Inject constructor(
    private val service: TheMovieDbService,
    private val db: PopularMovieDataBase,
    private val ratedDb: RatedMovieDataBase
) {
    suspend fun getPopularMovies(): List<PopularMovie> = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            if (movieCount() <= 0) {
                val movies = service.getPopularMovies().results
                insertMovies(movies.map { movie -> convertToPopularMovie(movie) })
            }
            getAll()
        }
    }

    suspend fun getTopRatedMovies(): List<RatedMovie> = withContext(Dispatchers.IO) {
        with(ratedDb.ratedMovieDao()) {
            if (movieCount() <= 0) {
                val movies = service.getTopRatedMovies().results
                insertMovies(movies.map { movie -> convertToRatedMovie(movie) })
            }
            getAll()
        }
    }

    suspend fun getVideos(id: Int): List<Result> =
        service.getVideos(BuildConfig.BASE_URL + "movie/" + id).videos.results


    private fun convertToPopularMovie(movie: ServerMovie) = PopularMovie(
        0,
        movie.title,
        movie.overview,
        movie.release_date,
        movie.poster_path,
        movie.backdrop_path ?: movie.poster_path,
        movie.original_language,
        movie.original_title,
        movie.popularity,
        movie.vote_average,
        false,
        movie.id
    )

    private fun convertToRatedMovie(movie: ServerMovie) = RatedMovie(
        0,
        movie.title,
        movie.overview,
        movie.release_date,
        movie.poster_path,
        movie.backdrop_path ?: movie.poster_path,
        movie.original_language,
        movie.original_title,
        movie.popularity,
        movie.vote_average,
        false,
        movie.id
    )
}

