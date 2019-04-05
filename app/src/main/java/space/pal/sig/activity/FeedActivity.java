package space.pal.sig.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.adapter.OtherFeedAdapter;
import space.pal.sig.fragment.FeedFragment;
import space.pal.sig.model.Feed;
import space.pal.sig.util.Utils;

public class FeedActivity extends AppCompatActivity {

    private ImageView newsImage;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private RelativeLayout noContent;

    private Feed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        newsImage = findViewById(R.id.news_image);
        TextView newsTitle = findViewById(R.id.news_title);
        TextView newsAbstract = findViewById(R.id.news_abstract);
        coordinatorLayout = findViewById(R.id.coordinator);
        noContent = findViewById(R.id.no_content);
        progressBar = findViewById(R.id.progress_bar);
        linearLayout = findViewById(R.id.content);
        RecyclerView recyclerView = findViewById(R.id.news_recycler);
        OtherFeedAdapter otherFeedAdapter = new OtherFeedAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(otherFeedAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        feed = getIntent().getParcelableExtra(FeedFragment.NEWSP_OBJECT);
        List<Feed> feedList = getIntent().getParcelableArrayListExtra("objects");
        Collections.shuffle(feedList);
        otherFeedAdapter.addItems(feedList);
        boolean connected = Utils.isNetworkConnected(this);
        if (connected) {
            newsTitle.setText(feed.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newsAbstract.setText(Html.fromHtml(feed.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                newsAbstract.setText(Html.fromHtml(feed.getDescription()));
            }
            Glide.with(FeedActivity.this)
                    .load(feed.getImage_square_large())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {
                            newsImage.setImageDrawable(resource);
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(FeedActivity.this, linearLayout,
                                    500, android.R.anim.fade_in);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(FeedActivity.this, linearLayout,
                                    500, android.R.anim.fade_in);
                            super.onLoadFailed(errorDrawable);
                        }
                    });
        } else {
            notifyInternet();
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            noContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feed.getLink()));
                startActivity(browserIntent);
                return true;
            case R.id.share:
                String packageName = getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = feed.getTitle().concat("\n");
                extraText += "See more here" + feed.getLink() + " or download the app:\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> recreate());
        snackbar.show();
    }
}
