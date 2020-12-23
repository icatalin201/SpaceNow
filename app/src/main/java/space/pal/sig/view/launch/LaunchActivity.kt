package space.pal.sig.view.launch

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
import space.pal.sig.R
import space.pal.sig.databinding.ActivityLaunchBinding
import space.pal.sig.model.dto.LaunchDto
import java.util.*

class LaunchActivity : AppCompatActivity() {

    companion object {
        const val LAUNCH_ID = "launch_id"
    }

    private lateinit var binding: ActivityLaunchBinding
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        setupCoverAndToolbar()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    private fun showLaunch(launch: LaunchDto) {
        val rocket = launch.rocket
        if (launch.upcoming) {
            val now = Calendar.getInstance().timeInMillis
            val countDown: Long = launch.dateUnix - now
            countDownTimer = object : CountDownTimer(countDown, 1000) {
                override fun onTick(l: Long) {
                    var millis = l
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val daysInMilli = hoursInMilli * 24
                    val d = millis / daysInMilli
                    millis %= daysInMilli
                    val h = millis / hoursInMilli
                    millis %= hoursInMilli
                    val m = millis / minutesInMilli
                    millis %= minutesInMilli
                    val s = millis / secondsInMilli
                    val dString = formatNumber(d)
                    val hString = formatNumber(h)
                    val mString = formatNumber(m)
                    val sString = formatNumber(s)
                }

                override fun onFinish() {}
            }
            countDownTimer?.start()
        }
    }

    private fun setupCoverAndToolbar() {
        val titleBarHeight: Int = getStatusBarHeight()
        val params = binding.launchCoverLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.bottomMargin = -titleBarHeight
        binding.launchCoverLayout.layoutParams = params
        setSupportActionBar(binding.launchToolbar)
        title = ""
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun formatNumber(number: Long): String {
        return if (number < 10) {
            String.format("0%s", number)
        } else {
            number.toString()
        }
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources
                .getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}