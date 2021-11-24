package com.oaso.movie_series_rappi.rated_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.CoroutinesTestRule
import com.oaso.movie_series_rappi.mockRatedMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.top_rated.TopRatedViewModel
import com.oaso.movie_series_rappi.ui.top_rated.TopRatedViewModel.UiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RatedViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var viewModel: TopRatedViewModel

    @Before
    fun setUp() {
        viewModel = TopRatedViewModel(repository)
    }

    @Test
    fun `load rated movies when the user pull the list`() {
        runBlocking {
            val movies = listOf(mockRatedMovie.copy(id=1))
            whenever(repository.getMoreTopRatedMovies(2)).thenReturn(movies)
            viewModel.model.observeForever(observer)
            viewModel.loadMoreRatedMovies(2)
            verify(observer).onChanged(UiModel.LoadMoreMovies(movies))
        }
    }

    @Test
    fun `load rated movies for the first time`() {
        runBlocking {
            val movies = listOf(mockRatedMovie.copy())
            whenever(repository.getTopRatedMovies()).thenReturn(movies)
            viewModel.model.observeForever(observer)
            verify(observer).onChanged(UiModel.Content(movies))
//            verify(observer).onChanged(UiModel.Content(movies))
        }
    }
}