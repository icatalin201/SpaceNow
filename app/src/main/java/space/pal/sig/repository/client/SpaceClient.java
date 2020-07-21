package space.pal.sig.repository.client;

import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class SpaceClient extends BaseClient {

    private static final String SPACE_API = "https://www.space-infinity.i-catalin.ro";

    @Inject
    public SpaceClient(OkHttpClient okHttpClient, Gson gson) {
        super(okHttpClient, gson);
    }

    @Override
    public String getApiUrl() {
        return SPACE_API;
    }

}
