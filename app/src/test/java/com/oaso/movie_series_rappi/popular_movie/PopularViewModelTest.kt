package com.oaso.movie_series_rappi.popular_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.CoroutinesTestRule
import com.oaso.movie_series_rappi.mockPopularMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.popular.PopularViewModel
import com.oaso.movie_series_rappi.ui.popular.PopularViewModel.UiModel
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
class PopularViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var viewModel: PopularViewModel

    @Before
    fun setUp(){
        viewModel = PopularViewModel(repository)
    }

    @Test
    fun `load more popular movies when the user pull the list`() {
        runBlocking {
            val movies = listOf(mockPopularMovie.copy(id = 1))
            whenever(repository.getMorePopularMovies(2)).thenReturn(movies)
            viewModel.model.observeForever(observer)
            viewModel.loadMorePopularMovies(2)
            verify(observer).onChanged(UiModel.LoadMoreMovies(movies))
        }
    }

    @Test
    fun `load popular movies for first time`(){
        runBlocking {
            val movies = listOf(mockPopularMovie.copy())
            whenever(repository.getPopularMovies()).thenReturn(movies)
            viewModel.model.observeForever(observer)
            verify(observer).onChanged(UiModel.Content(movies))
        }
    }
}

