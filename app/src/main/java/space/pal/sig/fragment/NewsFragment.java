package space.pal.sig.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.pal.sig.R;
import space.pal.sig.activity.NewsActivity;
import space.pal.sig.adapter.NewsAdapter;
import space.pal.sig.model.NewsPreview;
import space.pal.sig.model.NewsRelease;
import space.pal.sig.network.Client;
import space.pal.sig.network.Service;
import space.pal.sig.util.Utils;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private RelativeLayout loadingBar;
    private Context context;
    private NewsAdapter newsAdapter;
    private RelativeLayout noContent;
    private LinearLayout content;

    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    public static final String NEWSP_OBJECT = "news";

    private TextView title;
    private TextView description;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        progressBar = view.findViewById(R.id.progress_bar);
        loadingBar = view.findViewById(R.id.loading);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        noContent = view.findViewById(R.id.no_content);
        content = view.findViewById(R.id.content);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        imageView = view.findViewById(R.id.image);
        CardView cardView = view.findViewById(R.id.card);
        cardView.setOnClickListener(this);
        context = getActivity();
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(context, new ArrayList<>());
        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int firstVisibleItem = lm.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    makeCall();
                    loading = true;
                }
            }
        });
        if (context != null) {
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar())
                    .setTitle(getString(R.string.news));
            boolean connected = Utils.isNetworkConnected(context);
            if (connected) {
                makeLatestCall();
                makeCall();
            }
            else {
                notifyInternet();
                errorLayout();
            }
        }
        return view;
    }

    private void errorLayout() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        noContent.setVisibility(View.VISIBLE);
        Utils.customAnimation(context, noContent,
                500, android.R.anim.fade_in);
    }

    private void okLayout() {
        progressBar.setVisibility(View.GONE);
        noContent.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        Utils.customAnimation(context, content,
                500, android.R.anim.fade_in);
    }

    private void makeLatestCall() {
        new GetNews().execute(Utils.HUBBLE_URL.concat(Utils.NEWS_RELEASE_LAST));
    }

    private void makeCall() {
        if (presentPage > 1) {
            loadingBar.setVisibility(View.VISIBLE);
        }
        Service service = Client.getRetrofitClient(Utils.HUBBLE_URL).create(Service.class);
        Call<List<NewsPreview>> newsCall = service.getNewsPreview(presentPage);
        newsCall.enqueue(new Callback<List<NewsPreview>>() {
            @Override
            public void onResponse(Call<List<NewsPreview>> call, Response<List<NewsPreview>> response) {
                if (response.isSuccessful()) {
                    List<NewsPreview> newsPreviewList = response.body();
                    if (newsPreviewList != null && presentPage == 1) {
                        newsPreviewList.remove(0);
                    }
                    getNews(newsPreviewList);
                }
                else {
                    errorCall();
                    loadingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NewsPreview>> call, Throwable t) {
                errorCall();
                loadingBar.setVisibility(View.GONE);
            }
        });
    }

    private void getNews(List<NewsPreview> newsPreviews) {
        new GetOtherNews(newsPreviews).execute(Utils.HUBBLE_URL.concat(Utils.NEWS_RELEASE));
    }

    private void errorCall() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something has gone wrong. :(", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, NewsActivity.class);
        NewsRelease newsPreview = (NewsRelease) title.getTag();
        intent.putExtra(NEWSP_OBJECT, newsPreview);
        intent.putParcelableArrayListExtra("objects", newsAdapter.getNewsPreviewList());
        context.startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetNews extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject jsonObject = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.connect();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (java.io.IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                NewsRelease newsRelease = new NewsRelease();
                newsRelease.setKeystone_image_1x(jsonObject.getString("keystone_image_1x"));
                newsRelease.setName(jsonObject.getString("name"));
                newsRelease.setNews_id(jsonObject.getString("news_id"));
                newsRelease.setUrl(jsonObject.getString("url"));
                newsRelease.setPublication(jsonObject.getString("publication"));
                newsRelease.setAbstract(jsonObject.getString("abstract"));
                newsRelease.setThumbnail_1x(jsonObject.getString("thumbnail_1x"));
                newsRelease.setThumbnail_2x(jsonObject.getString("thumbnail_2x"));
                newsRelease.setThumbnail_retina(jsonObject.getString("thumbnail_retina"));
                title.setText(newsRelease.getName());
                title.setTag(newsRelease);
                description.setText(newsRelease.getAbstract());
                Glide.with(context)
                        .load(newsRelease.getThumbnail_retina())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                errorCall();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetOtherNews extends AsyncTask<String, Void, List<NewsRelease>> {

        private List<NewsPreview> newsPreviewList;

        GetOtherNews(List<NewsPreview> newsPreviews) {
            this.newsPreviewList = newsPreviews;
        }

        @Override
        protected List<NewsRelease> doInBackground(String... urls) {
            List<NewsRelease> newsReleaseList = new ArrayList<>();
            try {
                for (NewsPreview newsPreview : newsPreviewList) {
                    URL url = new URL(urls[0].replace("{news_id}", newsPreview.getNews_id()));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.connect();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    NewsRelease newsRelease = new NewsRelease();
                    newsRelease.setName(jsonObject.getString("name"));
                    newsRelease.setNews_id(jsonObject.getString("news_id"));
                    newsRelease.setUrl(jsonObject.getString("url"));
                    newsRelease.setPublication(jsonObject.getString("publication"));
                    newsRelease.setAbstract(jsonObject.getString("abstract"));
                    newsRelease.setMission(jsonObject.getString("mission"));
                    newsRelease.setThumbnail(jsonObject.getString("thumbnail"));
                    newsRelease.setThumbnail_1x(jsonObject.getString("thumbnail_1x"));
                    newsRelease.setThumbnail_2x(jsonObject.getString("thumbnail_2x"));
                    newsRelease.setThumbnail_retina(jsonObject.getString("thumbnail_retina"));
                    if (jsonObject.has("credits")) {
                        newsRelease.setCredits(jsonObject.getString("credits"));
                    }
                    if (jsonObject.has("keystone_image_1x")) {
                        newsRelease.setKeystone_image_1x(jsonObject.getString("keystone_image_1x"));
                    }
                    if (jsonObject.has("keystone_image_2x")) {
                        newsRelease.setKeystone_image_2x(jsonObject.getString("keystone_image_2x"));
                    }
                    newsReleaseList.add(newsRelease);
                }
            } catch (java.io.IOException | JSONException e) {
                e.printStackTrace();
            }
            return newsReleaseList;
        }

        @Override
        protected void onPostExecute(List<NewsRelease> newsReleases) {
            super.onPostExecute(newsReleases);
            newsAdapter.addItems(newsReleases);
            if (presentPage == 1) {
                okLayout();
            }
            presentPage++;
            loadingBar.setVisibility(View.GONE);
        }
    }
}
