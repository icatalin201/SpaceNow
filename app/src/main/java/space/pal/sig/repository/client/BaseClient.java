package space.pal.sig.repository.client;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@RequiredArgsConstructor
public abstract class BaseClient {

    private static Retrofit CLIENT;
    private final OkHttpClient okHttpClient;
    private final Gson gson;

    public <T> T createService(final Class<T> service) {
        return getClient().create(service);
    }

    public abstract String getApiUrl();

    private Retrofit getClient() {
        if (CLIENT == null) {
            synchronized (BaseClient.class) {
                CLIENT = new Retrofit.Builder()
                        .baseUrl(getApiUrl())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
            }
        }
        return CLIENT;
    }

}
