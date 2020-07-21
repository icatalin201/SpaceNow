package space.pal.sig.service;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import retrofit2.Response;
import space.pal.sig.model.News;
import space.pal.sig.repository.NewsRepository;
import space.pal.sig.repository.dto.NewsDto;
import space.pal.sig.repository.dto.NewsPreviewDto;
import space.pal.sig.repository.service.HubbleService;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@RequiredArgsConstructor
public class DownloadTask extends AsyncTask<Void, Void, Boolean> {

    private final HubbleService hubbleService;
    private final NewsRepository newsRepository;
    private final CompleteListener listener;

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            List<News> newsList = new ArrayList<>();
            Response<List<NewsPreviewDto>> response = hubbleService.getHubbleFeed(1).execute();
            List<NewsPreviewDto> newsPreviewDtoList = response.body();
            assert newsPreviewDtoList != null;
            for (NewsPreviewDto newsPreviewDto : newsPreviewDtoList) {
                Response<NewsDto> newsResponse = hubbleService
                        .getHubbleNews(newsPreviewDto.getNewsId()).execute();
                NewsDto newsDto = newsResponse.body();
                if (newsDto == null || newsDto.getAbstractText() == null) continue;
                newsList.add(newsDto.toNewsModel());
            }
            newsRepository.insertOrUpdate(newsList).subscribe();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        listener.onComplete();
    }
}
