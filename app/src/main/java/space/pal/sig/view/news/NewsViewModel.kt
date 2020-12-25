package space.pal.sig.view.news

import android.app.Application
import androidx.lifecycle.LiveData
import space.pal.sig.model.entity.News
import space.pal.sig.repository.NewsRepository
import space.pal.sig.view.BaseViewModel

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class NewsViewModel(
        application: Application,
        newsRepository: NewsRepository
) : BaseViewModel(application) {

    private val newsList = newsRepository.findAll()

    fun getNewsList(): LiveData<MutableList<News>> {
        return newsList
    }

}