package space.pal.sig.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.dto.NewsDto;
import space.pal.sig.repository.dto.NewsPreviewDto;
import space.pal.sig.repository.service.HubbleService;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NewsDownloadManager extends Worker {

    public NewsDownloadManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        HubbleService service = component.hubbleService();
        NewsRepository repository = component.newsRepository();
        try {
            downloadNews(service, repository);
            SpaceNotificationManager.createNotification(getApplicationContext(),
                    "News Sync Complete");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }

    private void downloadNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<NewsPreviewDto>> response = service.getHubbleFeed().execute();
        List<NewsPreviewDto> newsPreviewDtoList = response.body();
        assert newsPreviewDtoList != null;
        for (NewsPreviewDto newsPreviewDto : newsPreviewDtoList) {
            Response<NewsDto> newsResponse = service
                    .getHubbleNews(newsPreviewDto.getNewsId()).execute();
            NewsDto newsDto = newsResponse.body();
            if (newsDto == null || newsDto.getAbstractText() == null) continue;
            repository.insertOrUpdate(newsDto.toNewsModel()).subscribe();
        }
    }
}
