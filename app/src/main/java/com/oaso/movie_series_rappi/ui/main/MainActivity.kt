package com.oaso.movie_series_rappi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.databinding.ActivityMainBinding
import com.oaso.movie_series_rappi.ui.common.startActivity
import com.oaso.movie_series_rappi.ui.detail.DetailMovieActivity
import com.oaso.movie_series_rappi.ui.main.MainViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.navigation.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                startActivity<DetailMovieActivity> {
                    putExtra(DetailMovieActivity.MOVIE, it)
                }
            }
        })
    }

    private fun updateUi(model: UiModel) {
        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> {
                binding.progress.visibility = View.GONE
                adapter.popularMovies = model.popularMovies
            }
        }

    }
}