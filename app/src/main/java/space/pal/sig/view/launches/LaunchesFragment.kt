package space.pal.sig.view.launches

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentLaunchesBinding
import space.pal.sig.model.entity.LaunchWithData
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
        setHasOptionsMenu(true)
        viewModel.getLaunches()
                .observe(viewLifecycleOwner) { showLaunches(it) }
        viewModel.getSelectedOption()
                .observe(viewLifecycleOwner) { showOption(it) }
        adapter = LaunchesAdapter(requireContext())
        binding.launchesRecycler.adapter = adapter
        binding.launchesRecycler.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            val dialog = AlertDialog.Builder(requireContext(), R.style.SpaceNow_Dialog)
                    .setTitle(R.string.filter)
                    .setItems(viewModel.getOptions()) { d: DialogInterface, w: Int ->
                        viewModel.filter(w)
                        d.dismiss()
                    }.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showOption(option: String?) {
        option?.let {
            requireActivity().title = option
        }
    }

    private fun showLaunches(launches: MutableList<LaunchWithData>) {
        adapter.submit(launches)
    }

}