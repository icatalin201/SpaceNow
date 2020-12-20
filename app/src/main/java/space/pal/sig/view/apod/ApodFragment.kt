package space.pal.sig.view.apod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import space.pal.sig.R
import space.pal.sig.databinding.FragmentApodBinding
import space.pal.sig.view.BaseFragment

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class ApodFragment : BaseFragment() {

    private lateinit var binding: FragmentApodBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_apod,
                container, false)
        return binding.root
    }

}