package space.pal.sig.old.service.download;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import space.pal.sig.old.model.Apod;
import space.pal.sig.old.model.Launch;
import space.pal.sig.old.model.NewsSource;
import space.pal.sig.old.repository.ApodRepository;
import space.pal.sig.old.repository.LaunchesRepository;
import space.pal.sig.old.repository.NewsRepository;
import space.pal.sig.old.repository.dto.ApodDto;
import space.pal.sig.old.repository.dto.FeedDto;
import space.pal.sig.old.repository.dto.LaunchDto;
import space.pal.sig.old.repository.dto.LaunchResponse;
import space.pal.sig.old.repository.dto.NewsDto;
import space.pal.sig.old.repository.dto.NewsPreviewDto;
import space.pal.sig.old.repository.dto.PreviewLaunchDto;
import space.pal.sig.old.repository.service.HubbleService;
import space.pal.sig.old.repository.service.LaunchService;
import space.pal.sig.old.repository.service.NasaService;

import static space.pal.sig.old.repository.service.NasaService.API_KEY;

/**
 * SpaceNow
 * Created by Catalin on 7/31/2020
 **/
public class DataSyncUtil {

    public static void downloadApod(NasaService service, ApodRepository repository) throws IOException {
        Response<ApodDto> response = service.getPictureOfTheDay(API_KEY).execute();
        ApodDto apodDto = response.body();
        if (apodDto == null) return;
        Apod apod = apodDto.toApod();
        Apod apod1 = repository.findApodById(apod.getId());
        if (apod1 != null) {
            apod.setFavorite(apod1.getFavorite());
        }
        repository.insertOrUpdate(apod).subscribe();
    }

    public static void downloadEsaNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<FeedDto>> response = service.getEuropeanSpaceAgencyFeed(1).execute();
        List<FeedDto> feedDtoList = response.body();
        assert feedDtoList != null;
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto == null || feedDto.getImageSquare() == null
                    || feedDto.getImageSquareLarge() == null) continue;
            repository.insertOrUpdate(feedDto.toNewsModel(NewsSource.EUROPEAN_SPACE_AGENCY))
                    .subscribe();
        }
    }

    public static void downloadHubbleNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<NewsPreviewDto>> response = service.getHubbleFeed(1).execute();
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

    public static void downloadJwNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<FeedDto>> response = service.getJamesWebbSpaceTelescopeFeed(1).execute();
        List<FeedDto> feedDtoList = response.body();
        assert feedDtoList != null;
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto == null || feedDto.getImageSquare() == null
                    || feedDto.getImageSquareLarge() == null) continue;
            repository.insertOrUpdate(feedDto.toNewsModel(NewsSource.JAMES_WEBB_SPACE_TELESCOPE))
                    .subscribe();
        }
    }

    public static void downloadStNews(HubbleService service, NewsRepository repository) throws IOException {
        Response<List<FeedDto>> response = service.getSpaceTelescopeLiveFeed(1).execute();
        List<FeedDto> feedDtoList = response.body();
        assert feedDtoList != null;
        for (FeedDto feedDto : feedDtoList) {
            if (feedDto == null || feedDto.getImageSquare() == null
                    || feedDto.getImageSquareLarge() == null) continue;
            repository.insertOrUpdate(feedDto.toNewsModel(NewsSource.SPACE_TELESCOPE_LIVE))
                    .subscribe();
        }
    }

    public static void downloadNextLaunches(LaunchService service, LaunchesRepository repository) throws IOException {
        Response<LaunchResponse> response = service
                .getUpcomingLaunches(100).execute();
        LaunchResponse launchResponse = response.body();
        assert launchResponse != null;
        for (PreviewLaunchDto previewLaunchDto : launchResponse.getLaunches()) {
            Response<LaunchDto> launchDtoResponse = service.getLaunch(previewLaunchDto.getId()).execute();
            LaunchDto launchDto = launchDtoResponse.body();
            if (launchDto != null) {
                Launch launch = launchDto.toLaunchModel();
                Launch launch1 = repository.findLaunchById(launch.getId());
                if (launch1 != null) {
                    launch.setFavorite(launch1.getFavorite());
                }
                repository.insertOrUpdate(launch).subscribe();
            }
        }
    }

    private static void writeFile(Context context, String filename, String json) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(json.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
