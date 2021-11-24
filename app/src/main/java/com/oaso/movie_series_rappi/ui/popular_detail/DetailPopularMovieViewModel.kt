package com.oaso.movie_series_rappi.ui.popular_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.model.server.models.videos.Result
import com.oaso.movie_series_rappi.ui.popular.NavPopularMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPopularMovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private lateinit var movie: NavPopularMovie

    sealed class UiModel {
        data class Content(val movie: NavPopularMovie) : UiModel()
        data class PlayVideo(val result: Result) : UiModel()
        object notFoundVideos : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                getDetailMovie()
            }
            return _model
        }

    fun setMovie(movie: NavPopularMovie) {
        this.movie = movie
    }

    fun playVideoTrailer() {
        viewModelScope.launch {
            val videos = moviesRepository.getVideos(movie.movieId)
                .filter { result -> result.type.contains("Trailer") }
            if(videos.isNotEmpty()){
                val video = videos[0]
                _model.value = UiModel.PlayVideo(video)
            }else{
                _model.value = UiModel.notFoundVideos
            }

        }
    }

    private fun getDetailMovie() {
        _model.value = UiModel.Content(movie)
    }
}