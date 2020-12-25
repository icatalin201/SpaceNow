package space.pal.sig.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import space.pal.sig.database.dao.NewsDao
import space.pal.sig.model.dto.NewsDto
import space.pal.sig.model.dto.NewsPreviewDto
import space.pal.sig.model.entity.News
import space.pal.sig.network.HubbleService

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class NewsRepository(
        private val newsDao: NewsDao,
        private val hubbleService: HubbleService
) {

    fun save(news: News) {
        newsDao.save(news)
    }

    fun findAll(): LiveData<MutableList<News>> {
        return newsDao.findAll()
    }

    fun downloadHubbleFeed(page: Int): Single<List<NewsPreviewDto>> {
        return hubbleService.getHubbleFeed(page)
    }

    fun downloadHubbleNews(newsId: String): Single<NewsDto> {
        return hubbleService.getHubbleNews(newsId)
    }

    fun downloadEuropeanSpaceAgencyFeed(page: Int): Single<List<NewsDto>> {
        return hubbleService.getEuropeanSpaceAgencyFeed(page)
    }

    fun downloadJamesWebbSpaceTelescopeFeed(page: Int): Single<List<NewsDto>> {
        return hubbleService.getJamesWebbSpaceTelescopeFeed(page)
    }

    fun downloadSpaceTelescopeLiveFeed(page: Int): Single<List<NewsDto>> {
        return hubbleService.getSpaceTelescopeLiveFeed(page)
    }

}