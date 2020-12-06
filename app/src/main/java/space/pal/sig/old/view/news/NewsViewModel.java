package space.pal.sig.old.view.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import space.pal.sig.old.model.News;
import space.pal.sig.old.model.NewsSource;
import space.pal.sig.old.repository.NewsRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NewsViewModel extends AndroidViewModel {

    private final String[] newsSources = new String[]{
            "Hubble",
            "James Webb",
            "European Space Agency",
            "Space Telescope"
    };
    private final NewsRepository newsRepository;
    private final MutableLiveData<NewsSource> selectedSource = new MutableLiveData<>();
    private final MediatorLiveData<PagedList<News>> newsList = new MediatorLiveData<>();
    private LiveData<PagedList<News>> newsLiveData;

    public NewsViewModel(@NonNull Application application, NewsRepository newsRepository) {
        super(application);
        this.newsRepository = newsRepository;
        setSource("Hubble");
    }

    public void setSource(String source) {
        selectedSource.setValue(NewsSource.fromText(source));
    }

    public void findNewsBySource(NewsSource source) {
        newsList.removeSource(newsLiveData);
        newsLiveData = newsRepository.findAllBySource(source, 50);
        newsList.addSource(newsLiveData, newsList::setValue);
    }

    public LiveData<PagedList<News>> getNewsList() {
        return newsList;
    }

    public LiveData<NewsSource> getSelectedSource() {
        return selectedSource;
    }

    public String[] getNewsSources() {
        return newsSources;
    }
}
