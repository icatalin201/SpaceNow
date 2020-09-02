package space.pal.sig.injection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import space.pal.sig.repository.client.HubbleClient;
import space.pal.sig.repository.client.LaunchClient;
import space.pal.sig.repository.client.NasaClient;
import space.pal.sig.repository.client.SpaceClient;
import space.pal.sig.repository.service.HubbleService;
import space.pal.sig.repository.service.LaunchService;
import space.pal.sig.repository.service.NasaService;

@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    NasaService nasaService(NasaClient nasaClient) {
        return nasaClient.createService(NasaService.class);
    }

    @Provides
    @Singleton
    HubbleService hubbleService(HubbleClient hubbleClient) {
        return hubbleClient.createService(HubbleService.class);
    }

    @Provides
    @Singleton
    LaunchService launchService(LaunchClient launchClient) {
        return launchClient.createService(LaunchService.class);
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
    LaunchClient launchClient(OkHttpClient okHttpClient, Gson gson) {
        return new LaunchClient(okHttpClient, gson);
    }

}
