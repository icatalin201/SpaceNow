package space.pal.sig.view.extra

import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import space.pal.sig.R
import space.pal.sig.databinding.ActivityWebBinding
import space.pal.sig.view.BaseActivity

class WebActivity : BaseActivity() {

    companion object {
        const val URL = "url"
        const val TITLE = "title"
    }

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        setSupportActionBar(binding.webToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
        val url = intent.getStringExtra(URL)
        val title = intent.getStringExtra(TITLE)
        this.title = title
        setupWebView(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupWebView(url: String?) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
            ) {
                handler?.proceed()
            }
        }
        url?.let { binding.webView.loadUrl(it) }
    }
}