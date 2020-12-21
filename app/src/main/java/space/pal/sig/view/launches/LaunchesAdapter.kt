package space.pal.sig.view.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import space.pal.sig.R
import space.pal.sig.databinding.LaunchViewBinding
import space.pal.sig.model.Launch

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class LaunchesAdapter : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    companion object {
        private const val TOP = 1
        private const val MIDDLE = 2
        private const val BOTTOM = 3
        private const val TOP_BOTTOM = 4
    }

    private val launchesList = mutableListOf<Launch>()

    fun submit(launches: List<Launch>) {
        launchesList.clear()
        launchesList.addAll(launches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LaunchViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.launch_view, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(launchesList[position], getItemViewType(position))
    }

    override fun getItemCount(): Int {
        return launchesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && position == itemCount - 1) {
            TOP_BOTTOM
        } else if (position == 0) {
            TOP
        } else if (position == itemCount - 1) {
            BOTTOM
        } else {
            MIDDLE
        }
    }

    class ViewHolder(
            private val binding: LaunchViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(launch: Launch, viewType: Int) {
            val params = binding.launchLayout.layoutParams as RecyclerView.LayoutParams
            when (viewType) {
                TOP_BOTTOM -> {
                    binding.line.setBackgroundResource(R.drawable.launches_line_top_bottom)
                    params.topMargin = 33
                }
                TOP -> {
                    binding.line.setBackgroundResource(R.drawable.launches_line_top)
                    params.topMargin = 33
                }
                BOTTOM -> {
                    binding.line.setBackgroundResource(R.drawable.launches_line_bottom)
                    params.bottomMargin = 33
                }
                else -> binding.line.setBackgroundResource(R.drawable.launches_line_middle)
            }
            binding.launchLayout.layoutParams = params
        }

    }
}