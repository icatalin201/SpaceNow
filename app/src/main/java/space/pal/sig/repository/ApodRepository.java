package space.pal.sig.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import space.pal.sig.api.ApodService;
import space.pal.sig.api.GalleryService;
import space.pal.sig.db.ApodDao;
import space.pal.sig.model.Apod;
import space.pal.sig.model.dto.ApodDto;
import space.pal.sig.model.dto.SpaceResponse;
import space.pal.sig.util.DownloadListener;

public class ApodRepository {

    private final ApodService apodService;
    private final GalleryService galleryService;
    private final String apiKey;
    private final ApodDao apodDao;

    @Inject
    public ApodRepository(ApodDao apodDao, GalleryService galleryService,
                          ApodService apodService, String apiKey) {
        this.apodDao = apodDao;
        this.galleryService = galleryService;
        this.apodService = apodService;
        this.apiKey = apiKey;
    }

    public void create(Apod... apods) {
        apodDao.create(apods);
    }

    public void update(Apod... apods) {
        apodDao.update(apods);
    }

    public void delete(Apod... apods) {
        apodDao.delete(apods);
    }

    public Single<ApodDto> apod() {
        return apodService
                .apod(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public void download() {
        Disposable disposable = apodService
                .apod(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(apodDto -> apodDto.getMediaType().equals("image"))
                .subscribe(apodDto -> create(apodDto.toApod()));
    }

    public void downloadAll(DownloadListener downloadListener) {
        Disposable disposable = galleryService
                .download()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(SpaceResponse::getResult)
                .subscribe(apodDtos -> {
                    for (ApodDto apodDto : apodDtos) {
                        create(apodDto.toApod());
                    }
                    downloadListener.onStepCompleted();
                });
    }

    public LiveData<List<Apod>> findAll() {
        return apodDao.findAll();
    }

    public LiveData<Apod> findByDate(String date) {
        return apodDao.findByDate(date);
    }
    
    public LiveData<Apod> findLast() {
        return apodDao.findLast();
    }

}
