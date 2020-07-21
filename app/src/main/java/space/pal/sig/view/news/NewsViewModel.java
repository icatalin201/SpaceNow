package space.pal.sig.view.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import space.pal.sig.model.News;
import space.pal.sig.repository.NewsRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NewsViewModel extends AndroidViewModel {

    private final NewsRepository newsRepository;
    private final LiveData<PagedList<News>> newsList;

    public NewsViewModel(@NonNull Application application, NewsRepository newsRepository) {
        super(application);
        this.newsRepository = newsRepository;
        this.newsList = newsRepository.findAll(30);
    }

    public LiveData<PagedList<News>> getNewsList() {
        return newsList;
    }
}
