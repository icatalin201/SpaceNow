package space.pal.sig.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import space.pal.sig.api.SpaceFlightService;
import space.pal.sig.model.dto.SpaceFlightNewsDto;
import space.pal.sig.model.dto.SpaceFlightResponse;

public class SpaceFlightRepository {

    private final SpaceFlightService spaceFlightService;

    @Inject
    public SpaceFlightRepository(SpaceFlightService spaceFlightService) {
        this.spaceFlightService = spaceFlightService;
    }

    public Single<List<SpaceFlightNewsDto>> getArticles(int page, int limit) {
        return spaceFlightService
                .getArticles(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(SpaceFlightResponse::getDocs);
    }
}
