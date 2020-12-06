package space.pal.sig.old.view.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import space.pal.sig.old.model.News;
import space.pal.sig.old.repository.NewsRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
public class SingleNewsViewModel extends AndroidViewModel {

    private final NewsRepository newsRepository;
    private final MediatorLiveData<News> news = new MediatorLiveData<>();

    public SingleNewsViewModel(@NonNull Application application, NewsRepository newsRepository) {
        super(application);
        this.newsRepository = newsRepository;
    }

    public void loadNews(String id) {
        news.addSource(newsRepository.findById(id), news::setValue);
    }

    public LiveData<News> getNews() {
        return news;
    }
}
