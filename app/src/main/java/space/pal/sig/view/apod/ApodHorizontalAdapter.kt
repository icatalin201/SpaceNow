package space.pal.sig.view.apod

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.pal.sig.R
import space.pal.sig.databinding.ApodHorizontalCardViewBinding
import space.pal.sig.model.AstronomyPictureOfTheDay
import space.pal.sig.model.MediaType

/**
SpaceNow
Created by Catalin on 12/13/2020
 **/
class ApodHorizontalAdapter(
        private val listener: SelectApodListener
) : RecyclerView.Adapter<ApodHorizontalAdapter.ApodHorizontalViewHolder>() {

    companion object {
        private const val START = 1
        private const val MIDDLE = 2
        private const val END = 3
    }

    private val apodList: MutableList<AstronomyPictureOfTheDay> = mutableListOf()

    fun submit(apodList: MutableList<AstronomyPictureOfTheDay>) {
        this.apodList.clear()
        this.apodList.addAll(apodList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodHorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ApodHorizontalCardViewBinding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.apod_horizontal_card_view,
                        parent,
                        false
                )
        return ApodHorizontalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApodHorizontalViewHolder, position: Int) {
        holder.render(apodList[position], getItemViewType(position))
    }

    override fun getItemCount(): Int {
        return apodList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                START
            }
            itemCount - 1 -> {
                END
            }
            else -> {
                MIDDLE
            }
        }
    }

    inner class ApodHorizontalViewHolder(
            private val binding: ApodHorizontalCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetJavaScriptEnabled")
        fun render(apod: AstronomyPictureOfTheDay, viewType: Int) {
            val params = binding.apodCard.layoutParams as RecyclerView.LayoutParams
            params.setMargins(if (viewType == START) 30 else 0, 0, 30, 30)
            binding.apodCard.layoutParams = params
            if (apod.mediaType === MediaType.IMAGE) {
                binding.apodCardImage.contentDescription = apod.title
                Picasso.get().load(apod.url)
                        .centerCrop()
                        .fit()
                        .into(binding.apodCardImage)
            } else {
                binding.apodCardVideo.settings.javaScriptEnabled = true
                binding.apodCardVideo.loadUrl(apod.url)
                binding.apodCardVideo.webChromeClient = WebChromeClient()
            }
            binding.apodCardImage.isVisible = apod.mediaType == MediaType.IMAGE
            binding.apodCardVideo.isVisible = apod.mediaType == MediaType.VIDEO
        }

    }

}