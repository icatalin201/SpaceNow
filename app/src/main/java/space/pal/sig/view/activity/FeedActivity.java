package space.pal.sig.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.util.GlideApp;
import space.pal.sig.util.IntelViewModelFactory;
import space.pal.sig.view.viewmodel.MainViewModel;

import static space.pal.sig.util.DateTimeUtil.DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.DISPLAY_DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.dateToString;
import static space.pal.sig.util.DateTimeUtil.stringToDate;
import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.publication_date) TextView publicationDate;
    @BindView(R.id.layout) ConstraintLayout layout;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    @Inject IntelViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        Space.getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        setupLayout();
        setSupportActionBar(toolbar);
        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
        }
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                collapsingToolbarLayout.setTitle(mainViewModel.getSelectedFeed().getTitle());
            } else {
                collapsingToolbarLayout.setTitle("");
            }
        });
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        FeedDto feedDto = mainViewModel.getSelectedFeed();
        setupNews(feedDto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open) {
            FeedDto feedDto = mainViewModel.getSelectedFeed();
            if (feedDto.getLink() != null) {
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(TITLE, feedDto.getTitle());
                intent.putExtra(URL, feedDto.getLink());
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNews(FeedDto feedDto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.setText(Html.fromHtml(feedDto.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.setText(Html.fromHtml(feedDto.getDescription()));
        }
        name.setText(feedDto.getTitle());
        Date date = stringToDate(feedDto.getPubDate().split("T")[0], DATE_FORMAT);
        publicationDate.setText(String.format("Publication date: %s",
                dateToString(date, DISPLAY_DATE_FORMAT)));
        String thumbnail = feedDto.getImageSquareLarge();
        String url = "";
        if (thumbnail != null && !thumbnail.contains("https:")) {
            url = "https:" + thumbnail;
        }
        GlideApp.with(this)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .centerCrop()
                        .autoClone()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .error(R.drawable.ic_placeholder)
                .into(this.image);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupLayout() {
        int titleBarHeight = getStatusBarHeight();
        CollapsingToolbarLayout.LayoutParams params =
                (CollapsingToolbarLayout.LayoutParams) layout.getLayoutParams();
        params.bottomMargin = -titleBarHeight;
        layout.setLayoutParams(params);
    }
}
