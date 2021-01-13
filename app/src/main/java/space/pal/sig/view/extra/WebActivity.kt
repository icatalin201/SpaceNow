package space.pal.sig.view.extra

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import space.pal.sig.R
import space.pal.sig.databinding.ActivityWebBinding
import space.pal.sig.util.CustomWebViewClient
import space.pal.sig.view.BaseActivity

class WebActivity : BaseActivity() {

    companion object {
        const val URL = "url"
        const val TITLE = "title"
    }

    private lateinit var binding: ActivityWebBinding

    private val loadedRunnable = Runnable {
        binding.webView.isVisible = true
        binding.webLoading.isVisible = false
    }

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
        url?.let {
            binding.webView.webViewClient = CustomWebViewClient(loadedRunnable)
            binding.webView.loadUrl(it)
        }
    }
}