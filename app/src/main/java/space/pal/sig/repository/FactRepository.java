package space.pal.sig.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import space.pal.sig.api.FactService;
import space.pal.sig.db.FactDao;
import space.pal.sig.model.Fact;
import space.pal.sig.model.dto.FactDto;
import space.pal.sig.model.dto.SpaceResponse;
import space.pal.sig.util.DownloadListener;

public class FactRepository {

    private final FactService factService;
    private final FactDao factDao;

    @Inject
    public FactRepository(FactDao factDao, FactService factService) {
        this.factDao = factDao;
        this.factService = factService;
    }

    public void create(Fact... facts) {
        factDao.create(facts);
    }

    public void update(Fact... facts) {
        factDao.update(facts);
    }

    public void delete(Fact... facts) {
        factDao.delete(facts);
    }

    public void download(DownloadListener downloadListener) {
        Disposable disposable = factService
                .download()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(SpaceResponse::getResult)
                .subscribe(factDtos -> {
                    for (FactDto factDto : factDtos) {
                        create(factDto.toFact());
                    }
                    downloadListener.onStepCompleted();
                });
    }

    public LiveData<List<Fact>> findAll() {
        return factDao.findAll();
    }

}
