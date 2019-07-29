package space.pal.sig.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import space.pal.sig.api.ApodService;
import space.pal.sig.api.FactService;
import space.pal.sig.api.FeedService;
import space.pal.sig.api.GalleryService;
import space.pal.sig.api.GlossaryService;
import space.pal.sig.api.HubbleClient;
import space.pal.sig.api.NasaClient;
import space.pal.sig.api.SpaceClient;
import space.pal.sig.api.SpaceFlightClient;
import space.pal.sig.api.SpaceFlightService;
import space.pal.sig.db.ApodDao;
import space.pal.sig.db.FactDao;
import space.pal.sig.db.FeedDao;
import space.pal.sig.db.GlossaryDao;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.FactRepository;
import space.pal.sig.repository.FeedRepository;
import space.pal.sig.repository.GlossaryRepository;
import space.pal.sig.repository.SpaceFlightRepository;

@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    String apiKey() {
        return "BST2Npgb7FKgBIVzIW2Dd3qARcQbwgk1jdY5Hc28";
    }

    @Provides
    @Singleton
    ApodService apodService(NasaClient nasaClient) {
        return nasaClient.buildNasaClient().create(ApodService.class);
    }

    @Provides
    @Singleton
    FeedService feedService(HubbleClient hubbleClient) {
        return hubbleClient.buildHubbleClient().create(FeedService.class);
    }

    @Provides
    @Singleton
    GlossaryService glossaryService(HubbleClient hubbleClient) {
        return hubbleClient.buildHubbleClient().create(GlossaryService.class);
    }

    @Provides
    @Singleton
    GalleryService galleryService(SpaceClient spaceClient) {
        return spaceClient.buildSpaceClient().create(GalleryService.class);
    }

    @Provides
    @Singleton
    FactService factService(SpaceClient spaceClient) {
        return spaceClient.buildSpaceClient().create(FactService.class);
    }

    @Provides
    @Singleton
    SpaceFlightService spaceFlightService(SpaceFlightClient spaceFlightClient) {
        return spaceFlightClient.buildSpaceFlightClient().create(SpaceFlightService.class);
    }

    @Provides
    @Singleton
    ApodRepository apodRepository(ApodDao apodDao, GalleryService galleryService,
                                  ApodService apodService, String apiKey) {
        return new ApodRepository(apodDao, galleryService, apodService, apiKey);
    }

    @Provides
    @Singleton
    FeedRepository feedRepository(FeedDao feedDao, FeedService feedService) {
        return new FeedRepository(feedDao, feedService);
    }

    @Provides
    @Singleton
    GlossaryRepository glossaryRepository(GlossaryDao glossaryDao, GlossaryService glossaryService) {
        return new GlossaryRepository(glossaryDao, glossaryService);
    }

    @Provides
    @Singleton
    FactRepository factRepository(FactDao factDao, FactService factService) {
        return new FactRepository(factDao, factService);
    }

    @Provides
    @Singleton
    SpaceFlightRepository spaceFlightRepository(SpaceFlightService spaceFlightService) {
        return new SpaceFlightRepository(spaceFlightService);
    }

    @Provides
    @Singleton
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();
    }

    @Provides
    @Singleton
    NasaClient nasaClient(OkHttpClient okHttpClient, Gson gson) {
        return new NasaClient(okHttpClient, gson);
    }

    @Provides
    @Singleton
    HubbleClient hubbleClient(OkHttpClient okHttpClient, Gson gson) {
        return new HubbleClient(okHttpClient, gson);
    }

    @Provides
    @Singleton
    SpaceClient spaceClient(OkHttpClient okHttpClient, Gson gson) {
        return new SpaceClient(okHttpClient, gson);
    }

    @Provides
    @Singleton
    SpaceFlightClient spaceFlightClient(OkHttpClient okHttpClient, Gson gson) {
        return new SpaceFlightClient(okHttpClient, gson);
    }

}
