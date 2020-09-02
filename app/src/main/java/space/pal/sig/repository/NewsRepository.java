package space.pal.sig.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;
import space.pal.sig.model.News;
import space.pal.sig.model.NewsSource;
import space.pal.sig.model.database.NewsDao;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
@RequiredArgsConstructor
public class NewsRepository {

    private final NewsDao newsDao;

    public Completable insertOrUpdate(List<News> newsList) {
        return newsDao.insert(newsList.toArray(new News[0]));
    }

    public Completable insertOrUpdate(News news) {
        return newsDao.insert(news);
    }

    public LiveData<PagedList<News>> findAllBySource(NewsSource source, int pageSize) {
        return new LivePagedListBuilder<>(newsDao.findAllBySource(source), pageSize).build();
    }

    public LiveData<News> findById(String id) {
        return newsDao.findById(id);
    }

}
