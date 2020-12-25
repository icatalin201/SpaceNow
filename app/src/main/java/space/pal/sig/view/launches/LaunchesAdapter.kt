package space.pal.sig.view.launches

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.pal.sig.R
import space.pal.sig.databinding.LaunchViewBinding
import space.pal.sig.model.entity.LaunchWithData
import space.pal.sig.util.displayDatetime
import java.util.*
import kotlin.collections.HashMap

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class LaunchesAdapter(
        private val context: Context,
        private val listener: LaunchClickListener
) : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    companion object {
        private const val TOP = 1
        private const val MIDDLE = 2
        private const val BOTTOM = 3
        private const val TOP_BOTTOM = 4
    }

    private val launchesList = mutableListOf<LaunchWithData>()
    private val imagesMap = HashMap<LaunchWithData, String?>()

    fun submit(launches: List<LaunchWithData>) {
        launchesList.clear()
        launchesList.addAll(launches)
        setupImages(launches)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
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

    private fun setupImages(launches: List<LaunchWithData>) {
        launches.forEach { launch ->
            val rocket = launch.rocket
            var image: String? = null
            rocket?.let {
                image = when (val imagesSize = rocket.images.size) {
                    0 -> null
                    else -> {
                        val index = Random().nextInt(imagesSize)
                        rocket.images[index]
                    }
                }
            }
            imagesMap[launch] = image
        }
    }

    inner class ViewHolder(
            private val binding: LaunchViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(launchWithData: LaunchWithData, viewType: Int) {
            binding.launchLayout.setOnClickListener { listener.onClick(launchWithData) }
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
            val launch = launchWithData.launch
            val rocket = launchWithData.rocket
            val launchpad = launchWithData.launchpad
            val date = Date(launch.dateUnix * 1000).displayDatetime()
            binding.launchName.text = launch.name
            binding.launchDate.text = context.getString(R.string.launch_date_label, date)
            binding.launchAgency.text = context.getString(R.string.space_x_label)
            launchpad?.let {
                binding.launchLocation.text = it.fullName
            }
            rocket?.let {
                binding.launchRocket.text = it.name
            }
            val image: String? = imagesMap[launchWithData]
            image?.let {
                Picasso.get()
                        .load(it)
                        .fit()
                        .centerCrop()
                        .into(binding.launchImage)
            }
        }
    }
}