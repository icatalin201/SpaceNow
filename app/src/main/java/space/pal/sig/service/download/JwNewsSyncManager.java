package space.pal.sig.service.download;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.model.NewsSource;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.dto.FeedDto;
import space.pal.sig.repository.service.HubbleService;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
public class JwNewsSyncManager extends Worker {

    public JwNewsSyncManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent component = Space.getApplicationComponent();
        HubbleService service = component.hubbleService();
        NewsRepository repository = component.newsRepository();
        try {
            downloadJwNews(service, repository);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.Success.success();
    }

    private void downloadJwNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<FeedDto>> response = service.getJamesWebbSpaceTelescopeFeed(1).execute();
        List<FeedDto> feedDtoList = response.body();
        assert feedDtoList != null;
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto == null) continue;
            repository.insertOrUpdate(feedDto.toNewsModel(NewsSource.JAMES_WEBB_SPACE_TELESCOPE))
                    .subscribe();
        }
    }
}
