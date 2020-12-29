package space.pal.sig.view.iss

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivityIssBinding
import space.pal.sig.model.dto.IssPass
import space.pal.sig.model.dto.IssPosition
import space.pal.sig.util.DateExtensions.displayDatetime
import space.pal.sig.view.BaseActivity
import java.util.*

class IssActivity : BaseActivity() {

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 11
    }

    private lateinit var binding: ActivityIssBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: IssViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_iss)
        title = getString(R.string.iss)
        setSupportActionBar(binding.issToolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this)
        viewModel.getIssPosition()
                .observe(this) { showIssPosition(it) }
        viewModel.getIssPassList()
                .observe(this) { showIssPass(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_iss, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.iss_pass) {
            askForLocation()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            askForLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        viewModel.onPause()
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showIssPass(issPassList: List<IssPass>?) {
        issPassList?.let {
            if (issPassList.isNotEmpty()) {
                val date = Date(issPassList[0].risetime).displayDatetime()
                val dialog = AlertDialog.Builder(this, R.style.SpaceNow_Dialog)
                        .setTitle(R.string.app_name)
                        .setMessage(getString(R.string.iss_pass_message, date))
                        .setPositiveButton(R.string.ok, null)
                        .create()
                dialog.show()
            }
        }
    }

    private fun showIssPosition(issPosition: IssPosition?) {
        issPosition?.let {
            val fragment = supportFragmentManager
                    .findFragmentById(R.id.iss_map) as SupportMapFragment
            fragment.getMapAsync { googleMap ->
                googleMap.clear()
                googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                googleMap.uiSettings.isZoomGesturesEnabled = false
                googleMap.uiSettings.isScrollGesturesEnabled = false
                val latLng = LatLng(issPosition.latitude, issPosition.longitude)
                googleMap.addMarker(
                        MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_iss))
                                .zIndex(3.0f)
                                .title(getString(R.string.iss))
                )
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 1.0f)
                googleMap.animateCamera(cameraUpdate)
            }
        }
    }

    private fun askForLocation() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ), REQUEST_LOCATION_PERMISSION_CODE
            )
        } else {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { lastLocation ->
                    val latitude = lastLocation.latitude
                    val longitude = lastLocation.longitude
                    viewModel.askForIssPass(latitude, longitude)
                }
            }
        }
    }
}