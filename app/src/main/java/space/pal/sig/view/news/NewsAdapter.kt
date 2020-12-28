package space.pal.sig.view.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import space.pal.sig.R
import space.pal.sig.databinding.NewsViewBinding
import space.pal.sig.model.entity.News
import space.pal.sig.util.DateExtensions.displayDate

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class NewsAdapter(
        private val listener: NewsClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList: MutableList<News> = mutableListOf()

    fun submit(newsList: MutableList<News>) {
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: NewsViewBinding = DataBindingUtil
                .inflate(inflater, R.layout.news_view,
                        parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.render(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(
            private val binding: NewsViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(news: News) {
            binding.newsCardView.setOnClickListener { listener.onClick(news) }
            binding.newsTitle.text = news.title
            binding.newsDescription.text = news.description
            binding.newsSource.text = news.source.text
            binding.newsDate.text = news.date.displayDate()
            news.image?.let {
                Picasso.get()
                        .load("https:$it")
                        .fit()
                        .centerCrop()
                        .into(binding.newsImage)
            }
        }

    }
}
