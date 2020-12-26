package space.pal.sig.view.launch

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivityLaunchBinding
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.util.displayDatetime
import java.util.*

class LaunchActivity : AppCompatActivity() {

    companion object {
        const val LAUNCH_ID = "launch_id"
    }

    private lateinit var binding: ActivityLaunchBinding
    private val viewModel: LaunchViewModel by inject()
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        setSupportActionBar(binding.launchToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
        title = null
        val launchId = intent.getStringExtra(LAUNCH_ID)
        viewModel.submitLaunchId(launchId)
        viewModel.getLaunch()
                .observe(this) { showLaunch(it) }
        setupCoverAndToolbar()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun showLaunch(launchWithData: LaunchWithData) {
        val launch = launchWithData.launch
        val rocket = launchWithData.rocket
        val launchpad = launchWithData.launchpad
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
                    binding.days.text = dString
                    binding.hours.text = hString
                    binding.minutes.text = mString
                    binding.seconds.text = sString
                }

                override fun onFinish() {}
            }
            countDownTimer?.start()
            binding.launchClock.isVisible = true
            binding.divider2.isVisible = true
            binding.launchStatus.setBackgroundResource(R.drawable.launch_status_success)
            binding.launchStatus.text = getString(R.string.launch_available)
        } else {
            binding.launchClock.isVisible = false
            binding.divider2.isVisible = false
            binding.launchStatus.text = getString(R.string.launch_unknown)
            launch.success?.let {
                if (it) {
                    binding.launchStatus.setBackgroundResource(R.drawable.launch_status_success)
                    binding.launchStatus.text = getString(R.string.launch_success)
                } else {
                    binding.launchStatus.setBackgroundResource(R.drawable.launch_status_failure)
                    binding.launchStatus.text = getString(R.string.launch_failure)
                }
            }
        }
        binding.launchTitle.text = launch.name
        binding.launchDate.text = getString(R.string.launch_date_label,
                Date(launch.dateUnix).displayDatetime())
        launchpad?.let {
            binding.launchLocationName.text = it.fullName
        }
        rocket?.let {
            val image = when (val imagesSize = rocket.images.size) {
                0 -> null
                else -> {
                    val index = Random().nextInt(imagesSize)
                    rocket.images[index]
                }
            }
            image?.let {
                Picasso.get()
                        .load(it)
                        .fit()
                        .centerCrop()
                        .into(binding.launchRocketImageCover)
            }
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