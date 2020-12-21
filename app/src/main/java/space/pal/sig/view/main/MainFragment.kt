package space.pal.sig.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentMainBinding
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.model.Roadster
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                container, false)
        viewModel.getAstronomyPictureOfTheDay()
                .observe(viewLifecycleOwner) { showApod(it) }
        viewModel.getRoadster()
                .observe(viewLifecycleOwner) { showRoadster(it) }
        return binding.root
    }

    private fun showApod(apod: AstronomyPictureOfTheDay) {
        if (apod.mediaType == AstronomyPictureOfTheDay.IMAGE) {
            binding.mainApodImage.contentDescription = apod.title
            Picasso.get().load(apod.url)
                    .fit()
                    .centerCrop()
                    .into(binding.mainApodImage)
            binding.mainApodVideo.isVisible = false
            binding.mainApodImage.isVisible = true
        } else {
            binding.mainApodVideo.settings.javaScriptEnabled = true
            binding.mainApodVideo.webChromeClient = WebChromeClient()
            binding.mainApodVideo.loadUrl(apod.url)
            binding.mainApodVideo.isVisible = true
            binding.mainApodImage.isVisible = false
        }
    }

    private fun showRoadster(roadster: Roadster) {
        binding.mainRoadsterName.text = roadster.name
        binding.mainRoadsterEarthDistance.text = getString(
                R.string.earth_distance,
                roadster.earthDistanceKm)
        binding.mainRoadsterMarsDistance.text = getString(
                R.string.mars_distance,
                roadster.marsDistanceKm)
        binding.mainRoadsterSpeed.text = getString(
                R.string.roadster_speed,
                roadster.speedKph)
    }

}