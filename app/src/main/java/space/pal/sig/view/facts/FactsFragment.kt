package space.pal.sig.view.facts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentFactsBinding
import space.pal.sig.model.dto.FactDto
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/24/2020
 **/
class FactsFragment : BaseFragment(), FactClickListener {

    private lateinit var binding: FragmentFactsBinding
    private val viewModel: FactsViewModel by inject()
    private val adapter = FactsAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts,
                container, false)
        viewModel.getFacts()
                .observe(viewLifecycleOwner) { showFacts(it) }
        binding.factsRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.factsRecycler.adapter = adapter
        return binding.root
    }

    override fun onClick(fact: FactDto) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.SpaceNow_Dialog)
                .setTitle(R.string.did_you_know)
                .setMessage("Fact ${fact.index}\n\n${fact.content}")
                .setPositiveButton(R.string.share) { dialog, _ ->
                    share(fact.content)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.ok, null)
                .create()
        dialog.show()
    }

    private fun showFacts(facts: List<FactDto>) {
        adapter.submit(facts)
    }

    private fun share(content: String) {
        val appShareIntent = Intent(Intent.ACTION_SEND)
        appShareIntent.type = "text/plain"
        val extraText = getString(R.string.did_you_know) + "\r\n$content"
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText)
        startActivity(appShareIntent)
    }

}