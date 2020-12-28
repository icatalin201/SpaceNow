package space.pal.sig.view.extra

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import space.pal.sig.BuildConfig
import space.pal.sig.R
import space.pal.sig.databinding.ActivityAboutBinding
import space.pal.sig.view.BaseActivity

class AboutActivity : BaseActivity() {

    companion object {
        private const val POLICY_URL = "https://icatalin201.github.io/space/privacy_policy.html"
    }

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        setSupportActionBar(binding.aboutToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
        title = getString(R.string.about)
        binding.aboutVersion.text = BuildConfig.VERSION_NAME
        binding.aboutPrivacyBtn.setOnClickListener { openPrivacyPolicy() }
        binding.aboutReviewBtn.setOnClickListener { giveReview() }
        binding.aboutShareBtn.setOnClickListener { share() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun openPrivacyPolicy() {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.URL, POLICY_URL)
        intent.putExtra(WebActivity.TITLE, getString(R.string.privacy_policy))
        startActivity(intent)
    }

    private fun share() {
        val appShareIntent = Intent(Intent.ACTION_SEND)
        appShareIntent.type = "text/plain"
        val extraText = "https://play.google.com/store/apps/details?id=$packageName"
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText)
        startActivity(appShareIntent)
    }

    private fun giveReview() {
        val playStoreLink = "https://play.google.com/store/apps/details?id=$packageName"
        val app = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink))
        startActivity(app)
    }
}