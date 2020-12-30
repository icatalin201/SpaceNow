package space.pal.sig.view.splash

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivitySplashBinding
import space.pal.sig.view.BaseActivity
import space.pal.sig.view.main.MainActivity

class SplashActivity : BaseActivity() {

    companion object {
        const val SPLASH_SYNC_PROGRESS_ACTION = "SplashActivity.Broadcast.SyncProgress.Action"
        const val SYNC_MESSAGE = "SplashActivity.SyncMessage"
    }

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by inject()

    private val splashBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val message = intent.getStringExtra(SYNC_MESSAGE)
                binding.splashSyncMessage.text = message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_splash)
        registerSplashReceiver()
        viewModel.isSafeForStart().observe(this) { openMainActivity(it) }
    }

    override fun onDestroy() {
        unregisterReceiver(splashBroadcastReceiver)
        super.onDestroy()
    }

    private fun registerSplashReceiver() {
        val intentFilter = IntentFilter(SPLASH_SYNC_PROGRESS_ACTION)
        registerReceiver(splashBroadcastReceiver, intentFilter)
    }

    private fun openMainActivity(canOpen: Boolean) {
        binding.splashProgressBar.isVisible = !canOpen
        if (canOpen) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}