package com.oaso.movie_series_rappi.ui.top_rated_detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.oaso.movie_series_rappi.BuildConfig
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.ActivityDetailTopRatedBinding
import com.oaso.movie_series_rappi.ui.common.ConnectionLiveData
import com.oaso.movie_series_rappi.ui.common.loadUrl
import com.oaso.movie_series_rappi.ui.top_rated.NavRatedMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTopRatedActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailTopRatedActivity:movie"
    }

    private val viewModel: DetailTopRatedMovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailTopRatedBinding
    private lateinit var connection : ConnectionLiveData

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
        connection = ConnectionLiveData(application)
        connection.observe(this,::checkConnectionInternet)
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
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${trailer.key}"))
                intent.putExtra("force_fullscreen", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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

    private fun checkConnectionInternet(isConnected : Boolean){
        if (isConnected){
            binding.llConnection.visibility = View.GONE
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.llConnection.visibility = View.VISIBLE
            binding.fab.visibility = View.GONE
        }
    }
}