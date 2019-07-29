package space.pal.sig.api;

import com.google.gson.Gson;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LaunchClient {

    private static final String LAUNCH_URL = "https://launchlibrary.net/";
    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private static Retrofit client;

    @Inject
    public LaunchClient(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
    }

    public Retrofit buildLaunchClient() {
        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(LAUNCH_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return client;
    }

}
