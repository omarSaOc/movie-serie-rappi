package com.oaso.movie_series_rappi.ui.popular_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.databinding.ActivityDetailMovieBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.ui.common.loadUrl

class DetailPopularMovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailPopularMovieActivity:movie"
    }

    private val viewModelPopular: DetailPopularMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie: PopularMovie = intent.getParcelableExtra(MOVIE)
            ?: throw(IllegalAccessException("Movie not found"))
        viewModelPopular.setMovie(movie)
        viewModelPopular.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(modelPopular: DetailPopularMovieViewModel.UiModel) = with(binding) {
        val movie = modelPopular.movie
        movieDetailToolbar.title = movie.title
        movieImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)
    }
}