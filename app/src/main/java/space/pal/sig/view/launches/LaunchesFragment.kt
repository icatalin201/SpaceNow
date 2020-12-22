package space.pal.sig.view.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentLaunchesBinding
import space.pal.sig.model.Launch
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class LaunchesFragment : BaseFragment() {

    private lateinit var binding: FragmentLaunchesBinding
    private lateinit var adapter: LaunchesAdapter
    private val viewModel: LaunchesViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_launches,
                container, false)
        viewModel.getLaunches()
                .observe(viewLifecycleOwner) { showLaunches(it) }
        adapter = LaunchesAdapter(requireContext())
        binding.launchesRecycler.adapter = adapter
        binding.launchesRecycler.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    private fun showLaunches(launches: MutableList<Launch>) {
        adapter.submit(launches)
    }

}