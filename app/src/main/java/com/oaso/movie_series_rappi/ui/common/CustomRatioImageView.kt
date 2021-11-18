package com.oaso.movie_series_rappi.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.oaso.movie_series_rappi.R

class CustomRatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    companion object {
        const val DEFAULT_RATIO = 1F
    }

    private var ratio: Float = DEFAULT_RATIO

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomRatioImageView)
            with(a) {
                ratio = getFloat(R.styleable.CustomRatioImageView_ratio, DEFAULT_RATIO)
                recycle()
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) {
            return
        }

        if (width > 0) {
            height = (width * ratio).toInt()
        }else{
            width = (width * ratio).toInt()
        }

        setMeasuredDimension(width, height)
    }
}