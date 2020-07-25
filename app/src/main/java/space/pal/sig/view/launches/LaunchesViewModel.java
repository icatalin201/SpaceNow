package space.pal.sig.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import space.pal.sig.model.Launch;
import space.pal.sig.repository.LaunchesRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchesViewModel extends AndroidViewModel {

    private final LaunchesRepository launchesRepository;
    private final MediatorLiveData<PagedList<Launch>> launchList = new MediatorLiveData<>();
    private final MutableLiveData<Integer> selectedFilter = new MutableLiveData<>();
    private final String[] filterLaunches = new String[]{
            "Future Launches",
            "Past Launches"
    };
    private LiveData<PagedList<Launch>> launchesLiveData;

    public LaunchesViewModel(@NonNull Application application,
                             LaunchesRepository launchesRepository) {
        super(application);
        this.launchesRepository = launchesRepository;
        selectFilter(0);
    }

    public void filter(int pos) {
        if (pos == 0) {
            findFutureLaunches();
        } else {
            findPastLaunches();
        }
    }

    public void selectFilter(int pos) {
        this.selectedFilter.setValue(pos);
    }

    public LiveData<PagedList<Launch>> getLaunchList() {
        return launchList;
    }

    public LiveData<Integer> getSelectedFilter() {
        return selectedFilter;
    }

    public String[] getFilterLaunches() {
        return filterLaunches;
    }

    private void findPastLaunches() {
        launchList.removeSource(launchesLiveData);
        launchesLiveData = launchesRepository.findAllPastLaunches(50);
        launchList.addSource(launchesLiveData, launchList::setValue);
    }

    private void findFutureLaunches() {
        launchList.removeSource(launchesLiveData);
        launchesLiveData = launchesRepository.findAllFutureLaunches(50);
        launchList.addSource(launchesLiveData, launchList::setValue);
    }
}