package com.oaso.movie_series_rappi.rated_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.CoroutinesTestRule
import com.oaso.movie_series_rappi.mockNavRatedMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.top_rated_detail.DetailTopRatedMovieViewModel
import com.oaso.movie_series_rappi.ui.top_rated_detail.DetailTopRatedMovieViewModel.*
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
class DetailRatedViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    lateinit var observer : Observer<UiModel>

    private lateinit var viewModel : DetailTopRatedMovieViewModel

    @Before
    fun setUp(){
        viewModel = DetailTopRatedMovieViewModel(repository)
    }

    @Test
    fun `get the detail of the movie`(){
        viewModel.setMovie(mockNavRatedMovie)
        viewModel.model.observeForever(observer)
        verify(observer).onChanged(UiModel.Content(mockNavRatedMovie))
    }
}