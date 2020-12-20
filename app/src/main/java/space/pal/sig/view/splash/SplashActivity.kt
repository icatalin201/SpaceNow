package space.pal.sig.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivitySplashBinding
import space.pal.sig.old.view.main.MainActivity
import space.pal.sig.view.BaseActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_splash)
        viewModel.isSafeForStart().observe(this) { openMainActivity(it) }
    }

    private fun openMainActivity(canOpen: Boolean) {
        binding.splashProgressBar.isVisible = !canOpen
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}