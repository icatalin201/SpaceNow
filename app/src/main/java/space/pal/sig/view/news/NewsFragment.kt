package space.pal.sig.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import space.pal.sig.R
import space.pal.sig.databinding.FragmentNewsBinding
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news,
                container, false)
        return binding.root
    }

}