package space.pal.sig.util

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
SpaceNow
Created by Catalin on 12/29/2020
 **/
class CustomWebViewClient(
        private val url: String
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
    ): Boolean {
        val requestUrl = request.url
        if (requestUrl.host.equals(url)) {
            return false
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

}