package space.pal.sig.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;
import space.pal.sig.model.Fact;
import space.pal.sig.model.database.FactDao;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
@RequiredArgsConstructor
public class FactRepository {

    private final FactDao factDao;

    public Completable insertOrUpdate(List<Fact> factList) {
        return factDao.insert(factList.toArray(new Fact[0]));
    }

    public Completable insertOrUpdate(Fact fact) {
        return factDao.insert(fact);
    }

    public LiveData<List<Fact>> findAll() {
        return factDao.findAll();
    }

}
