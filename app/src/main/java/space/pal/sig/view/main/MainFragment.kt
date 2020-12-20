package space.pal.sig.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentMainBinding
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.model.Roadster

/**
 * SpaceNow
 * Created by Catalin on 12/20/2020
 **/
class MainFragment : Fragment() {

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

    }

    private fun showRoadster(roadster: Roadster) {

    }

}