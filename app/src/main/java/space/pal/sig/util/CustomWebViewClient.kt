package space.pal.sig.util

import android.net.http.SslError
import android.util.Log
import android.webkit.*

/**
SpaceNow
Created by Catalin on 12/29/2020
 **/
class CustomWebViewClient(
        private val loadedCallback: Runnable
) : WebViewClient() {

    companion object {
        private const val TAG = "CustomWebViewClient"
    }

    override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
    ): Boolean {
        Log.d(TAG, "shouldOverrideUrlLoading")
        return false
    }

    override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?) {
        Log.d(TAG, "onReceivedError $error")
        super.onReceivedError(view, request, error)
    }

    override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?) {
        Log.d(TAG, "onReceivedSslError $error")
        super.onReceivedSslError(view, handler, error)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Log.d(TAG, "onPageFinished")
        loadedCallback.run()
        super.onPageFinished(view, url)
    }

    override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?) {
        Log.d(TAG, "onReceivedHttpError $errorResponse")
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        Log.d(TAG, "onReceivedClientCertRequest")
        super.onReceivedClientCertRequest(view, request)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        Log.d(TAG, "onLoadResource")
        super.onLoadResource(view, url)
    }

}