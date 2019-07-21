package space.pal.sig.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import space.pal.sig.api.GlossaryService;
import space.pal.sig.db.GlossaryDao;
import space.pal.sig.model.Glossary;

public class GlossaryRepository {

    private final GlossaryDao glossaryDao;
    private final GlossaryService glossaryService;

    @Inject
    public GlossaryRepository(GlossaryDao glossaryDao, GlossaryService glossaryService) {
        this.glossaryDao = glossaryDao;
        this.glossaryService = glossaryService;
    }

    public void create(Glossary... glossaries) {
        glossaryDao.create(glossaries);
    }

    public void update(Glossary... glossaries) {
        glossaryDao.update(glossaries);
    }

    public void delete(Glossary... glossaries) {
        glossaryDao.delete(glossaries);
    }

    public LiveData<List<Glossary>> findAll() {
        return glossaryDao.findAll();
    }

    public void download() {
        Disposable disposable = glossaryService
                .glossary()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(glossaries -> create(glossaries.toArray(new Glossary[0])));
    }
}
