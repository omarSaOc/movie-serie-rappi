package com.oaso.movie_series_rappi.ui.popular_detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ActivityDetailMovieBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.ui.common.loadUrl
import com.oaso.movie_series_rappi.ui.popular.PlayVideoDialogFragment
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPopularMovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailPopularMovieActivity:movie"
    }

    private val viewModel: DetailPopularMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailMovieBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            viewModel.playVideoTrailer()
        }

        val movie: PopularMovie = intent.getParcelableExtra(MOVIE)
            ?: throw(IllegalAccessException("Movie not found"))
        viewModel.setMovie(movie)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) = with(binding) {
        when (model) {
            is UiModel.Content -> {
                val movie = model.movie
                movieDetailToolbar.title = movie.title
                movieImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
                movieDetailSummary.text = movie.overview
                movieDetailInfo.setMovie(movie)
            }
            is UiModel.PlayVideo -> {
                val trailer = model.result
                val dialogFragment =
                    PlayVideoDialogFragment(BuildConfig.BASE_YOUTUBE_URL + trailer.key)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.add(android.R.id.content, dialogFragment).addToBackStack(null).commit()
            }
            is UiModel.notFoundVideos -> {
                Snackbar.make(
                    findViewById(R.id.detailPopular),
                    "No se encontraron videos de esta película",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}