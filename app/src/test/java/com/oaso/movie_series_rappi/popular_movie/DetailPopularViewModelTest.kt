package com.oaso.movie_series_rappi.popular_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.CoroutinesTestRule
import com.oaso.movie_series_rappi.mockNavPopularMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieViewModel
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieViewModel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailPopularViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    lateinit var observer : Observer<UiModel>

    private lateinit var viewModel : DetailPopularMovieViewModel

    @Before
    fun setUp(){
        viewModel = DetailPopularMovieViewModel(repository)
    }

    @Test
    fun `get the detail of the movie`(){
        viewModel.setMovie(mockNavPopularMovie)
        viewModel.model.observeForever(observer)
        verify(observer).onChanged(UiModel.Content(mockNavPopularMovie))
    }
}