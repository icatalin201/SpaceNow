package space.pal.sig.view.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.pal.sig.R
import space.pal.sig.databinding.CrewMemberViewBinding
import space.pal.sig.model.entity.CrewMember

/**
 * SpaceNow
 * Created by Catalin on 12/26/2020
 **/
class CrewAdapter : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    private val crew: MutableList<CrewMember> = mutableListOf()

    fun submit(crew: List<CrewMember>) {
        this.crew.clear()
        this.crew.addAll(crew)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CrewMemberViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.crew_member_view, parent, false)
        return CrewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        holder.render(crew[position])
    }

    override fun getItemCount(): Int {
        return crew.size
    }

    inner class CrewViewHolder(
            private val binding: CrewMemberViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(crewMember: CrewMember) {
            binding.crewMemberName.text = crewMember.name
            Picasso.get()
                    .load(crewMember.image)
                    .fit()
                    .centerCrop()
                    .into(binding.crewMemberImage)
        }

    }
}
