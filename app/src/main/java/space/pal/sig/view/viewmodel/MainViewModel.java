package space.pal.sig.view.viewmodel;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import space.pal.sig.R;
import space.pal.sig.model.Apod;
import space.pal.sig.model.Fact;
import space.pal.sig.model.Glossary;
import space.pal.sig.model.NavigationItem;
import space.pal.sig.model.dto.ApodDto;
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.model.dto.NewsPreviewDto;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.FactRepository;
import space.pal.sig.repository.FeedRepository;
import space.pal.sig.repository.GlossaryRepository;
import space.pal.sig.view.fragment.ApodFragment;
import space.pal.sig.view.fragment.EsaFeedFragment;
import space.pal.sig.view.fragment.GlossaryFragment;
import space.pal.sig.view.fragment.HubbleFeedFragment;
import space.pal.sig.view.fragment.ImagesFragment;
import space.pal.sig.view.fragment.JwstFeedFragment;
import space.pal.sig.view.fragment.SpaceFragment;
import space.pal.sig.view.fragment.StFeedFragment;

import static space.pal.sig.model.NavigationItem.MENU_DIVIDER;
import static space.pal.sig.model.NavigationItem.MENU_ITEM;
import static space.pal.sig.util.DateTimeUtil.DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.dateToString;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Fragment> fragment = new MutableLiveData<>();
    private final MutableLiveData<Integer> title = new MutableLiveData<>();
    private final MutableLiveData<List<NavigationItem>> navigation = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final FeedRepository feedRepository;

    private final MediatorLiveData<Apod> apod = new MediatorLiveData<>();
    private final LiveData<List<Apod>> apods;
    private final LiveData<List<Glossary>> glossary;
    private final LiveData<List<Fact>> facts;
    private final MutableLiveData<List<FeedDto>> esaFeed = new MutableLiveData<>();
    private final MutableLiveData<List<FeedDto>> jwstFeed = new MutableLiveData<>();
    private final MutableLiveData<List<FeedDto>> stFeed = new MutableLiveData<>();
    private final MutableLiveData<List<NewsDto>> hubbleFeed = new MutableLiveData<>();

    private Apod selectedApod;
    private FeedDto selectedFeed;
    private NewsDto selectedNews;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    MainViewModel(ApodRepository apodRepository,
                  FeedRepository feedRepository,
                  FactRepository factRepository,
                  GlossaryRepository glossaryRepository) {
        this.feedRepository = feedRepository;
        navigation.setValue(navigationItems());
        glossary = glossaryRepository.findAll();
        apods = apodRepository.findAll();
        facts = factRepository.findAll();
        apod(apodRepository);
        downloadEsaFeed(1);
        downloadJwstFeed(1);
        downloadStFeed(1);
        downloadHubble(1);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public void downloadEsaFeed(int page) {
        Disposable disposable = feedRepository
                .esaFeed(page)
                .subscribe(esaFeed::setValue);
        compositeDisposable.add(disposable);
    }

    public void downloadJwstFeed(int page) {
        Disposable disposable = feedRepository
                .jwstFeed(page)
                .subscribe(jwstFeed::setValue);
        compositeDisposable.add(disposable);
    }

    public void downloadStFeed(int page) {
        Disposable disposable = feedRepository
                .spaceTelescopeFeed(page)
                .subscribe(stFeed::setValue);
        compositeDisposable.add(disposable);
    }

    public void downloadHubble(int page) {
        Disposable disposable = feedRepository
                .hubbleFeed(page)
                .subscribe(this::downloadHubble);
        compositeDisposable.add(disposable);
    }

    public Apod getSelectedApod() {
        return selectedApod;
    }

    public void setSelectedApod(Apod selectedApod) {
        this.selectedApod = selectedApod;
    }

    public FeedDto getSelectedFeed() {
        return selectedFeed;
    }

    public void setSelectedFeed(FeedDto selectedFeed) {
        this.selectedFeed = selectedFeed;
    }

    public NewsDto getSelectedNews() {
        return selectedNews;
    }

    public void setSelectedNews(NewsDto selectedNews) {
        this.selectedNews = selectedNews;
    }

    public MutableLiveData<List<FeedDto>> getEsaFeed() {
        return esaFeed;
    }

    public MutableLiveData<List<FeedDto>> getJwstFeed() {
        return jwstFeed;
    }

    public MutableLiveData<List<FeedDto>> getStFeed() {
        return stFeed;
    }

    public MutableLiveData<List<NewsDto>> getHubbleFeed() {
        return hubbleFeed;
    }

    public LiveData<List<Glossary>> getGlossary() {
        return glossary;
    }

    public LiveData<Apod> getApod() {
        return apod;
    }

    public LiveData<List<Apod>> getApods() {
        return apods;
    }

    public LiveData<List<Fact>> getFacts() {
        return facts;
    }

    public MutableLiveData<Boolean> isLoading() {
        return isLoading;
    }

    public MutableLiveData<Fragment> getFragment() {
        return fragment;
    }

    public MutableLiveData<Integer> getTitle() {
        return title;
    }

    public MutableLiveData<List<NavigationItem>> getNavigation() {
        return navigation;
    }

    public void setFragment(Fragment f) {
        fragment.setValue(f);
    }

    public void setTitle(Integer t) {
        title.setValue(t);
    }

    private List<NavigationItem> navigationItems() {
        List<NavigationItem> navigationItems = new ArrayList<>();
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.apod,
                R.drawable.icons8_planet_48, ApodFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.images,
                R.drawable.ic_baseline_collections_24px, ImagesFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.glossary,
                R.drawable.icons8_planetarium_48, GlossaryFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.facts,
                R.drawable.icons8_satellites_48, SpaceFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_DIVIDER, 0,
                0, null, null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.news,
                R.drawable.ic_baseline_library_books_24px, HubbleFeedFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.esa_feed,
                R.drawable.icons8_rocket_48, EsaFeedFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.jwst_feed,
                R.drawable.icons_telescope, JwstFeedFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.st_live_feed,
                R.drawable.icons8_satellite_signal_48, StFeedFragment.getInstance(), null));
        navigationItems.add(createNavigationItem(MENU_DIVIDER, 0,
                0, null, null));
//        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.settings,
//                R.drawable.ic_baseline_settings_20px, null, null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.about,
                R.drawable.ic_baseline_info_24px, null, null));
        navigationItems.add(createNavigationItem(MENU_DIVIDER, 0,
                0, null, null));
        navigationItems.add(createNavigationItem(MENU_ITEM, R.string.exit,
                R.drawable.ic_baseline_close_24px, null, null));
        return navigationItems;
    }

    private NavigationItem createNavigationItem(int type, int title, int icon,
                                                Fragment fragment, Intent intent) {
        NavigationItem navigationItem = new NavigationItem();
        navigationItem.setType(type);
        navigationItem.setTitle(title);
        navigationItem.setIcon(icon);
        navigationItem.setFragment(fragment);
        navigationItem.setIntent(intent);
        return navigationItem;
    }

    private void downloadHubble(List<NewsPreviewDto> newsPreviewDtos) {
        List<NewsDto> newsDtos = new ArrayList<>();
        for (NewsPreviewDto newsPreviewDto : newsPreviewDtos) {
            try {
                Response<NewsDto> response = feedRepository.hubbleNewsSync(newsPreviewDto.getNewsId());
                if (response.isSuccessful() && response.body() != null) {
                    newsDtos.add(response.body());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        handler.post(() -> hubbleFeed.setValue(newsDtos));
    }

    private void apod(ApodRepository apodRepository) {
        LiveData<Apod> apodLiveData = apodRepository
                .findByDate(dateToString(Calendar.getInstance(), DATE_FORMAT));
        LiveData<Apod> apodLiveData1 = apodRepository.findLast();
        apod.addSource(apodLiveData,
            apod1 -> {
                if (apod1 == null) {
                    Disposable disposable = apodRepository
                            .apod()
                            .filter(apodDto -> apodDto.getMediaType().equals("image"))
                            .map(ApodDto::toApod)
                            .subscribe(apod2 -> {
                                        apod.removeSource(apodLiveData);
                                        apod.setValue(apod2);
                                        new Thread(() -> apodRepository.create(apod2)).start();
                                    },
                                    throwable -> {},
                                    () -> {
                                        apod.removeSource(apodLiveData);
                                        apod.addSource(apodLiveData1, apod2 -> {
                                            apod.removeSource(apodLiveData1);
                                            apod.setValue(apod2);
                                        });
                                    }
                            );
                    compositeDisposable.add(disposable);
                } else {
                    apod.removeSource(apodLiveData);
                    apod.setValue(apod1);
                }
            });
    }

}
