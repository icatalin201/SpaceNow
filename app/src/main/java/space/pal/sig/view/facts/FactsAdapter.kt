package space.pal.sig.view.facts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import space.pal.sig.R
import space.pal.sig.databinding.FactViewBinding
import space.pal.sig.model.dto.FactDto

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class FactsAdapter(
        private val listener: FactClickListener
) : RecyclerView.Adapter<FactsAdapter.FactsViewHolder>() {

    private val facts: MutableList<FactDto> = mutableListOf()

    fun submit(facts: List<FactDto>) {
        this.facts.clear()
        this.facts.addAll(facts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FactViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.fact_view,
                        parent, false)
        return FactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        holder.render(facts[position])
    }

    override fun getItemCount(): Int {
        return facts.size
    }

    inner class FactsViewHolder(
            private val binding: FactViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(fact: FactDto) {
            binding.factTitle.text = fact.index
            binding.factCard.setOnClickListener { listener.onClick(fact) }
        }

    }
}