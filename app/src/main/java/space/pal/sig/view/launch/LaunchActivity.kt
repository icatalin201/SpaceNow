package space.pal.sig.view.launch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivityLaunchBinding
import space.pal.sig.model.entity.CrewMember
import space.pal.sig.model.entity.LaunchPad
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.util.ActivityExtensions.getStatusBarHeight
import space.pal.sig.util.DateExtensions.displayDatetime
import space.pal.sig.view.BaseActivity
import space.pal.sig.view.extra.WebActivity
import java.util.*

class LaunchActivity : BaseActivity(), CrewClickListener {

    companion object {
        const val LAUNCH_ID = "launch_id"
    }

    private lateinit var binding: ActivityLaunchBinding
    private val viewModel: LaunchViewModel by inject()
    private val adapter = CrewAdapter(this)
    private var countDownTimer: CountDownTimer? = null
    private var launchId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        launchId = intent.getStringExtra(LAUNCH_ID)
        viewModel.submitLaunchId(launchId)
        viewModel.getLaunch()
                .observe(this) { showLaunch(it) }
        viewModel.getCurrentRocketImage()
                .observe(this) { showRocketImage(it) }
        viewModel.getCurrentLaunchImage()
                .observe(this) { showLaunchImage(it) }
        viewModel.getCrew()
                .observe(this) { showCrew(it) }
        setupCoverAndToolbar()
        binding.launchNotificationsBtn
                .setOnClickListener { onNotificationBtnClick() }
        binding.launchWatchBtn
                .setOnClickListener { onWatchBtnClick() }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onClick(crewMember: CrewMember) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra(WebActivity.URL, crewMember.wikipedia)
        intent.putExtra(WebActivity.TITLE, crewMember.name)
        startActivity(intent)
    }

    private fun showRocketImage(image: String) {
        Picasso.get().load(image)
                .fit()
                .centerCrop()
                .into(binding.launchRocketImage)
    }

    private fun showLaunchImage(image: String) {
        Picasso.get().load(image)
                .fit()
                .centerCrop()
                .into(binding.launchRocketImageCover)
    }

    private fun showCrew(crew: List<CrewMember>?) {
        binding.launchCrewRecycler.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        binding.launchCrewRecycler.adapter = adapter
        crew?.let {
            adapter.submit(it)
            binding.launchCrewRecycler.isVisible = it.isNotEmpty()
        }
    }

    private fun showLaunch(launchWithData: LaunchWithData) {
        val launch = launchWithData.launch
        val rocket = launchWithData.rocket
        val launchpad = launchWithData.launchpad
        val now = Calendar.getInstance().timeInMillis
        val countDown: Long = launch.dateUnix - now
        if (countDown > 0) {
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
            binding.launchNotificationsBtn.isVisible = true
        } else {
            binding.launchCountdownLayout.isVisible = false
            binding.divider2.isVisible = false
            binding.launchStatusTv.text = getString(R.string.launch_unknown)
            binding.launchNotificationsBtn.isVisible = false
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
        binding.launchTitle.text = launch.name
        binding.launchDate.text = getString(R.string.launch_date_label,
                Date(launch.dateUnix).displayDatetime())
        launchpad?.let {
            binding.launchLocationTv.text = it.fullName
            binding.launchLocationCard.isVisible = true
            setupMap(it)
        }
        launch.details?.let {
            binding.launchDetailsTv.text = it
            binding.launchDetailsCard.isVisible = true
        }
        launch.links.webcast?.let {
            binding.launchWatchBtn.tag = it
            binding.launchWatchBtn.isVisible = true
        }
        rocket?.let {
            val name = "${it.name} | ${it.company}"
            binding.launchRocketName.text = name
            binding.launchRocketDescription.text = it.description
            binding.launchRocketCard.isVisible = true
        }
        handleNotificationBtn(launch.id)
    }

    private fun setupMap(launchpad: LaunchPad) {
        val fragment: SupportMapFragment = supportFragmentManager
                .findFragmentById(R.id.launch_location_map) as SupportMapFragment
        fragment.getMapAsync { googleMap ->
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            googleMap.uiSettings.isZoomGesturesEnabled = false
            googleMap.uiSettings.isScrollGesturesEnabled = false
            if (launchpad.latitude != null && launchpad.longitude != null) {
                val latLng = LatLng(launchpad.latitude, launchpad.longitude)
                googleMap.addMarker(
                        MarkerOptions().position(latLng).title(launchpad.name)
                )
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 7.0f)
                googleMap.animateCamera(cameraUpdate)
            }
        }
    }

    private fun handleNotificationBtn(launchId: String?) {
        val hasNotifications: Boolean = viewModel.hasNotifications(launchId)
        binding.launchNotificationsBtn.tag = hasNotifications
        if (hasNotifications) {
            binding.launchNotificationsBtn
                    .setImageResource(R.drawable.ic_baseline_notifications_active_24)
        } else {
            binding.launchNotificationsBtn
                    .setImageResource(R.drawable.ic_baseline_notifications_24)
        }
    }

    private fun onNotificationBtnClick() {
        val isOn = !(binding.launchNotificationsBtn.tag as Boolean)
        binding.launchNotificationsBtn.tag = isOn
        viewModel.toggleNotifications(isOn, launchId)
        handleNotificationBtn(launchId)
        val message = when (isOn) {
            true -> getString(R.string.launch_notification_on)
            else -> getString(R.string.launch_notification_off)
        }
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun onWatchBtnClick() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(binding.launchWatchBtn.tag as String)
        startActivity(intent)
    }

    private fun setupCoverAndToolbar() {
        val titleBarHeight: Int = getStatusBarHeight()
        val params = binding.launchCoverLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.bottomMargin = -titleBarHeight
        binding.launchCoverLayout.layoutParams = params
        title = null
        setSupportActionBar(binding.launchToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
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