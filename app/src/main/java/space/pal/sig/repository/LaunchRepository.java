package space.pal.sig.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import space.pal.sig.api.LaunchService;
import space.pal.sig.db.RocketDao;
import space.pal.sig.model.Rocket;
import space.pal.sig.model.dto.LaunchDto;
import space.pal.sig.model.dto.LaunchResponse;
import space.pal.sig.model.dto.RocketDto;
import space.pal.sig.model.dto.RocketsResponse;
import space.pal.sig.util.DownloadListener;

public class LaunchRepository {

    private final LaunchService launchService;
    private final RocketDao rocketDao;

    @Inject
    public LaunchRepository(LaunchService launchService, RocketDao rocketDao) {
        this.launchService = launchService;
        this.rocketDao = rocketDao;
    }

    public LiveData<List<Rocket>> findAllRockets() {
        return rocketDao.findAll();
    }

    public void createRockets(List<Rocket> rockets) {
        rocketDao.insert(rockets);
    }

    public void downloadRockets(DownloadListener downloadListener) {
        Disposable disposable = launchService
                .getRockets(0, 210)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(RocketsResponse::getRockets)
                .map(RocketDto::toRockets)
                .subscribe(rockets -> {
                    createRockets(rockets);
                    downloadListener.onStepCompleted();
                });
    }

    public Single<List<LaunchDto>> findNextLaunches() {
        return launchService
                .getNextLaunches(50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(LaunchResponse::getLaunches);
    }

    public Single<List<LaunchDto>> findLaunches(int offset, int count, String sort,
                                                String start, String end) {
        return launchService
                .getLaunches(offset, count, sort, start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(LaunchResponse::getLaunches);
    }

}
