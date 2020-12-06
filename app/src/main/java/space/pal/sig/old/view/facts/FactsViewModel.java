package space.pal.sig.old.view.facts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import space.pal.sig.old.model.Fact;
import space.pal.sig.old.repository.FactRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
public class FactsViewModel extends AndroidViewModel {

    private final LiveData<List<Fact>> facts;

    public FactsViewModel(@NonNull Application application, FactRepository factRepository) {
        super(application);
        facts = factRepository.findAll();
    }

    public LiveData<List<Fact>> getFacts() {
        return facts;
    }
}
