package space.pal.sig.view.splash;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.injection.ApplicationComponent;
import space.pal.sig.model.Launch;
import space.pal.sig.model.News;
import space.pal.sig.model.NewsSource;
import space.pal.sig.repository.LaunchesRepository;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.dto.FeedDto;
import space.pal.sig.repository.dto.LaunchDto;
import space.pal.sig.repository.dto.NewsDto;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
public class DataLoadWorker extends Worker {

    private final Context context;
    private final Gson gson;

    public DataLoadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public Result doWork() {
        ApplicationComponent applicationComponent = Space.getApplicationComponent();
        LaunchesRepository launchesRepository = applicationComponent.launchesRepository();
        NewsRepository newsRepository = applicationComponent.newsRepository();
        try {
            loadEsaFeed(newsRepository);
            loadHubbleFeed(newsRepository);
            loadJwStFeed(newsRepository);
            loadStLiveFeed(newsRepository);
            loadLaunches(launchesRepository);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }

    private void loadEsaFeed(NewsRepository repository) throws IOException {
        String esaFeedJson = readJsonFromResource(R.raw.esa_feed);
        FeedDto[] feedDtoList = gson.fromJson(esaFeedJson, FeedDto[].class);
        List<News> newsList = new ArrayList<>();
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto.getImageSquareLarge() == null) continue;
            newsList.add(feedDto.toNewsModel(NewsSource.EUROPEAN_SPACE_AGENCY));
        }
        repository.insertOrUpdate(newsList).subscribe();
    }

    private void loadHubbleFeed(NewsRepository repository) throws IOException {
        String hubbleFeedJson = readJsonFromResource(R.raw.hubble_feed);
        NewsDto[] newsDtoList = gson.fromJson(hubbleFeedJson, NewsDto[].class);
        List<News> newsList = new ArrayList<>();
        for (NewsDto newsDto : newsDtoList) {
            newsList.add(newsDto.toNewsModel());
        }
        repository.insertOrUpdate(newsList).subscribe();
    }

    private void loadJwStFeed(NewsRepository repository) throws IOException {
        String jwstFeedJson = readJsonFromResource(R.raw.jwst_feed);
        FeedDto[] feedDtoList = gson.fromJson(jwstFeedJson, FeedDto[].class);
        List<News> newsList = new ArrayList<>();
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto.getImageSquareLarge() == null) continue;
            newsList.add(feedDto.toNewsModel(NewsSource.JAMES_WEBB_SPACE_TELESCOPE));
        }
        repository.insertOrUpdate(newsList).subscribe();
    }

    private void loadStLiveFeed(NewsRepository repository) throws IOException {
        String stLiveFeedJson = readJsonFromResource(R.raw.st_live_feed);
        FeedDto[] feedDtoList = gson.fromJson(stLiveFeedJson, FeedDto[].class);
        List<News> newsList = new ArrayList<>();
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto.getImageSquareLarge() == null) continue;
            newsList.add(feedDto.toNewsModel(NewsSource.SPACE_TELESCOPE_LIVE));
        }
        repository.insertOrUpdate(newsList).subscribe();
    }

    private void loadLaunches(LaunchesRepository repository) throws IOException {
        String launchesJson = readJsonFromResource(R.raw.launches);
        LaunchDto[] launchDtoList = gson.fromJson(launchesJson, LaunchDto[].class);
        List<Launch> launchList = new ArrayList<>();
        for (LaunchDto launchDto : launchDtoList) {
            launchList.add(launchDto.toLaunchModel());
        }
        repository.insertOrUpdate(launchList).subscribe();
    }

    private String readJsonFromResource(int resource) throws IOException {
        InputStream is = context.getResources()
                .openRawResource(resource);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }
}
