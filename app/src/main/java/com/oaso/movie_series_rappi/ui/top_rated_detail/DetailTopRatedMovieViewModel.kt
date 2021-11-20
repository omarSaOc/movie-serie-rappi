package com.oaso.movie_series_rappi.ui.top_rated_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie

class DetailTopRatedMovieViewModel : ViewModel() {

    private lateinit var movie : RatedMovie

    class UiModel(val movie : RatedMovie)

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if (_model.value == null){
                _model.value = UiModel(movie)
            }
            return _model
        }

    fun setMovie(movie : RatedMovie){
        this.movie = movie
    }
}