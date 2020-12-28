package space.pal.sig.view.iss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

class IssActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIssBinding
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
        viewModel.getIssPosition()
                .observe(this) { showIssPosition(it) }
        viewModel.getIssPassList()
                .observe(this) { showIssPass(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showIssPass(issPassList: List<IssPass>?) {
        issPassList?.let {

        }
    }

    private fun showIssPosition(issPosition: IssPosition?) {
        issPosition?.let {
            val map = supportFragmentManager
                    .findFragmentById(R.id.iss_map) as SupportMapFragment
            map.getMapAsync { googleMap ->
                googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                googleMap.uiSettings.isZoomGesturesEnabled = false
                googleMap.uiSettings.isScrollGesturesEnabled = false
                googleMap.clear()
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
}