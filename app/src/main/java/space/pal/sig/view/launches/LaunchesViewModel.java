package space.pal.sig.view.launches;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import java.util.Calendar;

import space.pal.sig.model.Launch;
import space.pal.sig.repository.LaunchesRepository;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchesViewModel extends AndroidViewModel {

    private final LaunchesRepository launchesRepository;
    private final MediatorLiveData<PagedList<Launch>> launchList = new MediatorLiveData<>();
    private final MutableLiveData<String> selectedYear = new MutableLiveData<>();
    private final String[] filterYears = new String[]{
            "2022", "2021", "2020", "2019",
            "2018", "2017", "2016", "2015",
            "2014", "2013", "2012", "2011",
            "2010", "2009", "2008", "2007",
            "2006", "2005", "2004", "2003",
            "2000", "1999", "1998", "1997",
            "1996", "1995", "1994", "1993",
            "1992", "1991", "1990", "1989"
    };
    private LiveData<PagedList<Launch>> launchesLiveData;

    public LaunchesViewModel(@NonNull Application application,
                             LaunchesRepository launchesRepository) {
        super(application);
        this.launchesRepository = launchesRepository;
        selectYear("2020");
    }

    public void filterByYear(String year) {
        int y = Integer.parseInt(year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, 11, 31);
        launchList.removeSource(launchesLiveData);
        launchesLiveData = launchesRepository
                .findAllLaunchesByTimestampLowerThan(calendar.getTimeInMillis(), 50);
        launchList.addSource(launchesLiveData, launchList::setValue);
    }

    public void selectYear(String year) {
        this.selectedYear.setValue(year);
    }

    public LiveData<PagedList<Launch>> getLaunchList() {
        return launchList;
    }

    public LiveData<String> getSelectedYear() {
        return selectedYear;
    }

    public String[] getFilterYears() {
        return filterYears;
    }
}
