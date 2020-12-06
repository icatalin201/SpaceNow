package space.pal.sig.old.repository.client;

import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class HubbleClient extends BaseClient {

    private static final String HUBBLE_URL = "http://hubblesite.org";

    @Inject
    public HubbleClient(OkHttpClient okHttpClient, Gson gson) {
        super(okHttpClient, gson);
    }

    @Override
    public String getApiUrl() {
        return HUBBLE_URL;
    }
}
