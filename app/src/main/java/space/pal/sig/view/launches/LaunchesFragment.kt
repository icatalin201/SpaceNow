package space.pal.sig.view.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import space.pal.sig.R
import space.pal.sig.databinding.FragmentLaunchesBinding
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class LaunchesFragment : BaseFragment() {

    private lateinit var binding: FragmentLaunchesBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_launches,
                container, false)
        return binding.root
    }

}