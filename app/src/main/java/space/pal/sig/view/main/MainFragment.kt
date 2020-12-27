package space.pal.sig.view.main

import android.content.Intent
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
import space.pal.sig.view.BaseFragment
import space.pal.sig.view.apod.ApodActivity
import space.pal.sig.view.launch.LaunchActivity

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
        viewModel.getLatestLaunch()
                .observe(viewLifecycleOwner) { showLastLaunch(it) }
        return binding.root
    }

    private fun showNextLaunch(launchWithData: LaunchWithData?) {
        launchWithData?.let {
            val launch = it.launch
            binding.mainNextLaunchPreview.text = launch.name
            val rocket = launchWithData.rocket
            rocket?.let {
                val image: String? = when (rocket.images.size > 2) {
                    true -> rocket.images[1]
                    else -> null
                }
                image?.let {
                    Picasso.get()
                            .load(image)
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.launch)
                            .into(binding.mainNextLaunchImage)
                }
            }
            binding.mainNextLaunchCard.setOnClickListener {
                val intent = Intent(requireContext(), LaunchActivity::class.java)
                intent.putExtra(LaunchActivity.LAUNCH_ID, launch.id)
                startActivity(intent)
            }
        }
    }

    private fun showLastLaunch(launchWithData: LaunchWithData?) {
        launchWithData?.let {
            val launch = it.launch
            binding.mainLastLaunchPreview.text = launch.name
            val rocket = launchWithData.rocket
            rocket?.let {
                val image: String? = when (rocket.images.size > 3) {
                    true -> rocket.images[2]
                    else -> null
                }
                image?.let {
                    Picasso.get()
                            .load(image)
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.launch)
                            .into(binding.mainLastLaunchImage)
                }
            }
            binding.mainLastLaunchCard.setOnClickListener {
                val intent = Intent(requireContext(), LaunchActivity::class.java)
                intent.putExtra(LaunchActivity.LAUNCH_ID, launch.id)
                startActivity(intent)
            }
        }
    }

    private fun showApod(apod: AstronomyPictureOfTheDay?) {
        apod?.let {
            if (apod.mediaType == AstronomyPictureOfTheDayDto.IMAGE) {
                binding.mainApodTitle.setOnClickListener {
                    val intent = Intent(requireContext(), ApodActivity::class.java)
                    intent.putExtra(ApodActivity.APOD_DATE, apod.date)
                    startActivity(intent)
                }
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