package com.oaso.movie_series_rappi.ui.top_rated_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ActivityDetailTopRatedBinding
import com.oaso.movie_series_rappi.ui.common.loadUrl
import com.oaso.movie_series_rappi.ui.popular.PlayVideoDialogFragment
import com.oaso.movie_series_rappi.ui.top_rated.NavRatedMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTopRatedActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailTopRatedActivity:movie"
    }

    private val viewModel: DetailTopRatedMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailTopRatedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopRatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            viewModel.playVideoTrailer()
        }

        val movie: NavRatedMovie = intent.getParcelableExtra(MOVIE)
            ?: throw(IllegalAccessException("Movie not found"))
        viewModel.setMovie(movie)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailTopRatedMovieViewModel.UiModel) = with(binding) {
        when (model) {
            is DetailTopRatedMovieViewModel.UiModel.Content -> {
                val movie = model.movie
                movieDetailToolbar.title = movie.title
                movieImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
                movieDetailSummary.text = movie.overview
                movieDetailInfo.setMovie(movie)
            }
            is DetailTopRatedMovieViewModel.UiModel.PlayVideo -> {
                val trailer = model.result
                val dialogFragment =
                    PlayVideoDialogFragment(BuildConfig.BASE_YOUTUBE_URL + trailer.key)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.add(
                    android.R.id.content,
                    dialogFragment
                ).addToBackStack(null).commit()
            }
            is DetailTopRatedMovieViewModel.UiModel.notFoundVideos -> {
                Snackbar.make(
                    findViewById(R.id.detailTopRated),
                    "No se encontraron videos de esta película",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}