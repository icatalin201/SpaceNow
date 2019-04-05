package space.pal.sig.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.pal.sig.R;
import space.pal.sig.activity.FeedActivity;
import space.pal.sig.adapter.FeedAdapter;
import space.pal.sig.model.Feed;
import space.pal.sig.network.Client;
import space.pal.sig.network.Service;
import space.pal.sig.util.Utils;

public class FeedFragment extends Fragment implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private Context context;

    private ProgressBar progressBar;
    private RelativeLayout loadingBar;
    private FeedAdapter feedAdapter;
    private RelativeLayout noContent;
    private LinearLayout content;

    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    public static final String NEWSP_OBJECT = "news";
    public static final String FEED_SOURCE = "feed_source";

    private String feedType;

    private TextView title;
    private TextView description;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            feedType = bundle.getString(FEED_SOURCE);
        }
        coordinatorLayout = view.findViewById(R.id.coordinator);
        progressBar = view.findViewById(R.id.progress_bar);
        loadingBar = view.findViewById(R.id.loading);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        TextView latest = view.findViewById(R.id.latest);
        noContent = view.findViewById(R.id.no_content);
        content = view.findViewById(R.id.content);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        imageView = view.findViewById(R.id.image);
        CardView cardView = view.findViewById(R.id.card);
        cardView.setOnClickListener(this);
        context = getActivity();
        recyclerView.setHasFixedSize(true);
        feedAdapter = new FeedAdapter(context, new ArrayList<>());
        final LinearLayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(feedAdapter);
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
            String title = "";
            switch (feedType) {
                case Utils.ESA_FEED:
                    title = getString(R.string.esa_feed);
                    break;
                case Utils.JWST_FEED:
                    title = getString(R.string.jwst_feed);
                    break;
                case Utils.ST_LIVE_FEED:
                    latest.setText(R.string.hubble_now);
                    title = getString(R.string.st_live_feed);
                    break;
            }
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar())
                    .setTitle(title);
            boolean connected = Utils.isNetworkConnected(context);
            if (connected) {
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

    private void makeCall() {
        if (presentPage > 1) {
            loadingBar.setVisibility(View.VISIBLE);
        }
        Service service = Client.getRetrofitClient(Utils.HUBBLE_URL).create(Service.class);
        Call<List<Feed>> call = null;
        switch (feedType) {
            case Utils.ESA_FEED:
                call = service.getExternalESAFeed(presentPage);
                break;
            case Utils.JWST_FEED:
                call = service.getExternalJWSTFeed(presentPage);
                break;
            case Utils.ST_LIVE_FEED:
                call = service.getExternalSTFeed(presentPage);
                break;
        }
        if (call != null) {
            call.enqueue(new Callback<List<Feed>>() {
                @Override
                public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                    if (response.isSuccessful()) {
                        List<Feed> feedList = response.body();
                        if (presentPage == 1) {
                            if (feedList != null) {
                                Feed feed = feedList.get(0);
                                title.setText(feed.getTitle());
                                title.setTag(feed);
                                description.setText(feed.getDescription());
                                Glide.with(context)
                                        .load(feed.getThumbnail())
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource,
                                                                        @Nullable Transition<? super Drawable> transition) {
                                                imageView.setImageDrawable(resource);
                                                okLayout();
                                            }
                                        });
                                feedList.remove(0);
                            }
                        }
                        feedAdapter.addItems(feedList);
                        presentPage++;
                        loadingBar.setVisibility(View.GONE);
                    }
                    else {
                        errorCall();
                        loadingBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Feed>> call, Throwable t) {
                    errorCall();
                    loadingBar.setVisibility(View.GONE);
                }
            });
        }
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
        Intent intent = new Intent(context, FeedActivity.class);
        Feed feed = (Feed) title.getTag();
        intent.putExtra(NEWSP_OBJECT, feed);
        intent.putParcelableArrayListExtra("objects", feedAdapter.getList());
        context.startActivity(intent);
    }
}
