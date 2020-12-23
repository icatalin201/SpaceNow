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
import space.pal.sig.model.dto.AstronomyPictureOfTheDayDto
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.model.entity.Roadster
import space.pal.sig.util.displayDatetime
import space.pal.sig.view.BaseFragment
import java.util.*

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
        viewModel.getNextLaunch()
                .observe(viewLifecycleOwner) { showNextLaunch(it) }
        return binding.root
    }

    private fun showNextLaunch(launchWithData: LaunchWithData?) {
        launchWithData?.let {
            val launch = it.launch
            val date = Date(launch.dateUnix * 1000).displayDatetime()
            binding.mainLaunchPreview.text = getString(
                    R.string.launch_upcoming_preview,
                    launch.name,
                    date
            )
            val rocket = launchWithData.rocket
            val image: String? = when (rocket.images.size) {
                0 -> null
                else -> rocket.images[0]
            }
            image?.let {
                Picasso.get()
                        .load(image)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.launch)
                        .into(binding.mainLaunchImage)
            }
        }
    }

    private fun showApod(apod: AstronomyPictureOfTheDay?) {
        apod?.let {
            if (apod.mediaType == AstronomyPictureOfTheDayDto.IMAGE) {
                binding.mainApodImage.contentDescription = apod.title
                Picasso.get().load(apod.url)
                        .fit()
                        .centerCrop()
                        .into(binding.mainApodImage)
                binding.mainApodVideo.isVisible = false
                binding.mainApodImage.isVisible = true
                binding.mainApodTitle.isVisible = true
            } else {
                binding.mainApodVideo.settings.javaScriptEnabled = true
                binding.mainApodVideo.webChromeClient = WebChromeClient()
                binding.mainApodVideo.loadUrl(apod.url)
                binding.mainApodVideo.isVisible = true
                binding.mainApodImage.isVisible = false
                binding.mainApodTitle.isVisible = false
            }
        }
    }

    private fun showRoadster(roadster: Roadster?) {
        roadster?.let {
            binding.mainRoadsterName.text = roadster.name
            binding.mainRoadsterSpeed.text = getString(
                    R.string.roadster_speed,
                    roadster.speedKph)
        }
    }

}