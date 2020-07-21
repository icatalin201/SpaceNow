package space.pal.sig.repository.client;

import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchClient extends BaseClient {

    private static final String LAUNCH_URL = "https://launchlibrary.net/";

    @Inject
    public LaunchClient(OkHttpClient okHttpClient, Gson gson) {
        super(okHttpClient, gson);
    }

    @Override
    public String getApiUrl() {
        return LAUNCH_URL;
    }

}
