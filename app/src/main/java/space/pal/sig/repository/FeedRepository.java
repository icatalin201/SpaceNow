package space.pal.sig.repository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import space.pal.sig.api.FeedService;
import space.pal.sig.db.FeedDao;
import space.pal.sig.model.Feed;
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.model.dto.NewsPreviewDto;

public class FeedRepository {

    private final FeedDao feedDao;
    private final FeedService feedService;

    @Inject
    public FeedRepository(FeedDao feedDao, FeedService feedService) {
        this.feedDao = feedDao;
        this.feedService = feedService;
    }

    public void create(Feed... feeds) {
        feedDao.create(feeds);
    }

    public void update(Feed... feeds) {
        feedDao.update(feeds);
    }

    public void delete(Feed... feeds) {
        feedDao.delete(feeds);
    }

    public Single<List<FeedDto>> esaFeed(int page) {
        return feedService
                .esaFeed(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FeedDto>> esaFeed() {
        return feedService
                .esaFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FeedDto>> jwstFeed(int page) {
        return feedService
                .jwstFeed(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FeedDto>> jwstFeed() {
        return feedService
                .jwstFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FeedDto>> spaceTelescopeFeed(int page) {
        return feedService
                .spaceTelescopeFeed(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FeedDto>> spaceTelescopeFeed() {
        return feedService
                .spaceTelescopeFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<NewsPreviewDto>> hubbleFeed(int page) {
        return feedService
                .hubbleFeed(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Single<List<NewsPreviewDto>> hubbleFeed() {
        return feedService
                .hubbleFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Single<NewsDto> hubbleNews(String newsId) {
        return feedService
                .hubbleNews(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Response<NewsDto> hubbleNewsSync(String newsId) throws IOException {
        return feedService.hubbleNewsSync(newsId).execute();
    }

}
