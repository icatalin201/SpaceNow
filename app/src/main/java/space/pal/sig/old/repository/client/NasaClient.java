package space.pal.sig.old.repository.client;

import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NasaClient extends BaseClient {

    private static final String NASA_URL = "https://api.nasa.gov";

    @Inject
    public NasaClient(OkHttpClient okHttpClient, Gson gson) {
        super(okHttpClient, gson);
    }

    @Override
    public String getApiUrl() {
        return NASA_URL;
    }
}
