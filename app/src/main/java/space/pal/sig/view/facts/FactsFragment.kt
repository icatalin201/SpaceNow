package space.pal.sig.view.facts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentFactsBinding
import space.pal.sig.model.entity.Fact
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
class FactsFragment : BaseFragment() {

    private lateinit var binding: FragmentFactsBinding
    private val viewModel: FactsViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts,
                container, false)
        viewModel.getFacts()
                .observe(viewLifecycleOwner) { showFacts(it) }
        return binding.root
    }

    private fun showFacts(facts: MutableList<Fact>?) {

    }

}