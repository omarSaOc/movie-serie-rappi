package com.oaso.movie_series_rappi.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.oaso.movie_series_rappi.model.server.Movie

class MovieDetailInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    fun setMovie(movie: Movie) = with(movie) {
        text = buildSpannedString {
            bold { append("Original language: ") }
            appendLine(original_language)

            bold { append("Original title: ") }
            appendLine(original_title)

            bold { append("Release date: ") }
            appendLine(release_date)

            bold { append("Popularity: ") }
            appendLine(popularity.toString())

            bold { append("Vote Average: ") }
            append(vote_average.toString())
        }
    }
}