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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.adapter.OtherNewsAdapter;
import space.pal.sig.fragment.NewsFragment;
import space.pal.sig.model.NewsRelease;
import space.pal.sig.util.Utils;

public class NewsActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private NewsRelease newsRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        ImageView newsImage = findViewById(R.id.news_image);
        TextView newsTitle = findViewById(R.id.news_title);
        TextView newsAbstract = findViewById(R.id.news_abstract);
        coordinatorLayout = findViewById(R.id.coordinator);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        LinearLayout linearLayout = findViewById(R.id.content);
        RelativeLayout noContent = findViewById(R.id.no_content);
        RecyclerView recyclerView = findViewById(R.id.news_recycler);
        OtherNewsAdapter otherNewsAdapter = new OtherNewsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(otherNewsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newsRelease = getIntent().getParcelableExtra(NewsFragment.NEWSP_OBJECT);
        List<NewsRelease> newsReleaseList = getIntent().getParcelableArrayListExtra("objects");
        newsReleaseList.remove(newsRelease);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newsReleaseList.removeIf(item -> item.getNews_id().equals(newsRelease.getNews_id()));
        }
        else {
            Iterator<NewsRelease> newsReleaseIterator = newsReleaseList.iterator();
            while (newsReleaseIterator.hasNext()) {
                NewsRelease np = newsReleaseIterator.next();
                if (np.getNews_id().equals(newsRelease.getNews_id())) {
                    newsReleaseIterator.remove();
                }
            }
        }
        Collections.shuffle(newsReleaseList);
        otherNewsAdapter.addAll(newsReleaseList);
        boolean connected = Utils.isNetworkConnected(this);
        if (connected) {
            newsTitle.setText(newsRelease.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newsAbstract.setText(Html.fromHtml(newsRelease.getAbstract(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                newsAbstract.setText(Html.fromHtml(newsRelease.getAbstract()));
            }
            String url = newsRelease.getKeystone_image_2x() == null
                    ? newsRelease.getThumbnail_retina() : newsRelease.getKeystone_image_2x();
            Glide.with(this)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {
                            newsImage.setImageDrawable(resource);
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(NewsActivity.this, linearLayout,
                                    500, android.R.anim.fade_in);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            progressBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);
                            noContent.setVisibility(View.GONE);
                            Utils.customAnimation(NewsActivity.this, linearLayout,
                                    500, android.R.anim.fade_in);
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsRelease.getUrl()));
                startActivity(browserIntent);
                return true;
            case R.id.share:
                String packageName = getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = newsRelease.getName().concat("\n");
                extraText += "See more here" + newsRelease.getUrl() + " or download the app:\n";
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

    private void errorCall() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something has gone wrong. :(", 8000);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> recreate());
        snackbar.show();
    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> recreate());
        snackbar.show();
    }

}
