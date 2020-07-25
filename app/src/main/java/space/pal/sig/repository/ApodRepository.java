package space.pal.sig.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;
import space.pal.sig.model.Apod;
import space.pal.sig.model.database.ApodDao;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
@RequiredArgsConstructor
public class ApodRepository {

    private final ApodDao apodDao;

    public Completable insertOrUpdate(List<Apod> apodList) {
        return apodDao.insert(apodList.toArray(new Apod[0]));
    }

    public Completable insertOrUpdate(Apod apod) {
        return apodDao.insert(apod);
    }

    public LiveData<PagedList<Apod>> findAll(int pageSize) {
        return new LivePagedListBuilder<>(apodDao.findAll(), pageSize).build();
    }

    public LiveData<Apod> findByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return apodDao.findByDate(year, month, day);
    }

    public LiveData<List<Apod>> findAllBeforeOfDateWithLimit(Date date, int limit) {
        return apodDao.findAllBeforeOfDateWithLimit(date, limit);
    }

}
