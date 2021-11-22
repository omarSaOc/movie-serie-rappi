package com.oaso.movie_series_rappi.ui.popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatDialogFragment
import com.oaso.movie_series_rappi.R

class PlayVideoDialogFragment(private val url : String) : AppCompatDialogFragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_play_video, container, false)
        val webView = view.findViewById<WebView>(R.id.webView)
        webView.webChromeClient = object : WebChromeClient() {}
        webView.webViewClient = object : WebViewClient() {}
        val settings = webView.settings
        settings.javaScriptEnabled = true
        webView.loadUrl(url)

        return view
    }
}