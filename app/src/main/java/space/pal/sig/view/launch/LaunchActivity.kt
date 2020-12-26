package space.pal.sig.view.launch

import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivityLaunchBinding
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.util.ActivityExtensions.getStatusBarHeight
import space.pal.sig.util.displayDatetime
import space.pal.sig.view.BaseActivity
import java.util.*

class LaunchActivity : BaseActivity() {

    companion object {
        const val LAUNCH_ID = "launch_id"
    }

    private lateinit var binding: ActivityLaunchBinding
    private val viewModel: LaunchViewModel by inject()
    private val adapter = CrewAdapter()
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        setSupportActionBar(binding.launchToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
        val launchId = intent.getStringExtra(LAUNCH_ID)
        viewModel.submitLaunchId(launchId)
        binding.launchCrewRecycler.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        binding.launchCrewRecycler.adapter = adapter
        viewModel.getLaunch()
                .observe(this) { showLaunch(it) }
        viewModel.getCurrentImage()
                .observe(this) { showImage(it) }
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

    private fun showImage(image: String) {
        val picasso = Picasso.get()
        picasso.load(image).fit()
                .centerCrop()
                .into(binding.launchRocketImageCover)
        picasso.load(image)
                .fit()
                .centerCrop()
                .into(binding.launchRocketImage)
    }

    private fun showLaunch(launchWithData: LaunchWithData) {
        val launch = launchWithData.launch
        val rocket = launchWithData.rocket
        val launchpad = launchWithData.launchpad
        val crew = launchWithData.crew
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
            binding.launchCountdownLayout.isVisible = true
            binding.divider2.isVisible = true
            binding.launchStatusTv.setBackgroundResource(R.drawable.launch_status_success)
            binding.launchStatusTv.text = getString(R.string.launch_available)
        } else {
            binding.launchCountdownLayout.isVisible = false
            binding.divider2.isVisible = false
            binding.launchStatusTv.text = getString(R.string.launch_unknown)
            launch.success?.let {
                if (it) {
                    binding.launchStatusTv.setBackgroundResource(R.drawable.launch_status_success)
                    binding.launchStatusTv.text = getString(R.string.launch_success)
                } else {
                    binding.launchStatusTv.setBackgroundResource(R.drawable.launch_status_failure)
                    binding.launchStatusTv.text = getString(R.string.launch_failure)
                }
            }
        }
        crew?.let {
            adapter.submit(it)
            binding.launchCrewRecycler.isVisible = it.isNotEmpty()
        }
        binding.launchTitle.text = launch.name
        binding.launchDate.text = getString(R.string.launch_date_label,
                Date(launch.dateUnix).displayDatetime())
        launchpad?.let {
            binding.launchLocationTv.text = it.fullName
            binding.launchLocationCard.isVisible = true
        }
        launch.details?.let {
            binding.launchDetailsTv.text = it
            binding.launchDetailsCard.isVisible = true
        }
        rocket?.let {
            val name = "${it.name} | ${it.company}"
            binding.launchRocketName.text = name
            binding.launchRocketDescription.text = it.description
            binding.launchRocketCard.isVisible = true
        }
    }

    private fun setupCoverAndToolbar() {
        val titleBarHeight: Int = getStatusBarHeight()
        val params = binding.launchCoverLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.bottomMargin = -titleBarHeight
        binding.launchCoverLayout.layoutParams = params
        title = null
        setSupportActionBar(binding.launchToolbar)
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
}