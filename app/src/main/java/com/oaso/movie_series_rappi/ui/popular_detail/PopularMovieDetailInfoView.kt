package com.oaso.movie_series_rappi.ui.popular_detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie

class PopularMovieDetailInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    fun setMovie(movie: PopularMovie) = with(movie) {
        text = buildSpannedString {
            bold { append("Original language: ") }
            appendLine(originalLanguage)

            bold { append("Original title: ") }
            appendLine(originalTitle)

            bold { append("Release date: ") }
            appendLine(releaseDate)

            bold { append("Popularity: ") }
            appendLine(popularity.toString())

            bold { append("Vote Average: ") }
            append(voteAverage.toString())
        }
    }
}