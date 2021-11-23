package com.oaso.movie_series_rappi.model.server

import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovieDataBase
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovieDataBase
import com.oaso.movie_series_rappi.model.server.models.videos.Result
import com.oaso.movie_series_rappi.ui.common.transformToPopular
import com.oaso.movie_series_rappi.ui.common.transformToRated
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
                insertMovies(movies.map { movie -> movie.transformToPopular() })
            }
            getAll()
        }
    }

    suspend fun getMorePopularMovies(page: Int): List<PopularMovie> = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            val movies = service.getMorePopularMovies(page = page).results
            insertMovies(movies.map { movie -> movie.transformToPopular() })
            getAll()
        }
    }

    suspend fun getTopRatedMovies(): List<RatedMovie> = withContext(Dispatchers.IO) {
        with(ratedDb.ratedMovieDao()) {
            if (movieCount() <= 0) {
                val movies = service.getTopRatedMovies().results
                insertMovies(movies.map { movie -> movie.transformToRated() })
            }
            getAll()
        }
    }

    suspend fun getMoreTopRatedMovies(page : Int): List<RatedMovie> = withContext(Dispatchers.IO){
        with(ratedDb.ratedMovieDao()){
            val movies = service.getMoreTopRatedMovies(page = page).results
            insertMovies(movies.map { movie -> movie.transformToRated() })
            getAll()
        }
    }

    suspend fun getVideos(id: Int): List<Result> =
        service.getVideos(BuildConfig.BASE_URL + "movie/" + id).videos.results
}

