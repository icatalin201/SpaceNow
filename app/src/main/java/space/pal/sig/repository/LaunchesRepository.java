package space.pal.sig.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;
import space.pal.sig.model.Launch;
import space.pal.sig.model.database.LaunchDao;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@RequiredArgsConstructor
public class LaunchesRepository {

    private final LaunchDao launchDao;

    public Completable insertOrUpdate(List<Launch> launches) {
        return launchDao.insert(launches.toArray(new Launch[0]));
    }

    public Completable insertOrUpdate(Launch launch) {
        return launchDao.insert(launch);
    }

    public LiveData<PagedList<Launch>> findAllPastLaunches(int pageSize) {
        return new LivePagedListBuilder<>(launchDao
                .findAllPast(System.currentTimeMillis()), pageSize).build();
    }

    public LiveData<PagedList<Launch>> findAllFutureLaunches(int pageSize) {
        return new LivePagedListBuilder<>(launchDao
                .findAllFuture(System.currentTimeMillis()), pageSize).build();
    }

}
