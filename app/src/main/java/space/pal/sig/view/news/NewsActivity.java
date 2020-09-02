package space.pal.sig.view.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.News;
import space.pal.sig.view.SpaceBaseActivity;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class NewsActivity extends SpaceBaseActivity {

    public static final String NEWS_ID = "news_id";

    @BindView(R.id.news_image_cover)
    AppCompatImageView coverImage;
    @BindView(R.id.news_title)
    AppCompatTextView title;
    @BindView(R.id.news_toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_cover_layout)
    ConstraintLayout coverLayout;
    @BindView(R.id.news_text)
    AppCompatTextView text;
    @Inject
    SingleNewsViewModelFactory factory;
    private SingleNewsViewModel singleNewsViewModel;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Space.getApplicationComponent().inject(this);
        int titleBarHeight = getStatusBarHeight();
        CollapsingToolbarLayout.LayoutParams params =
                (CollapsingToolbarLayout.LayoutParams) coverLayout.getLayoutParams();
        params.bottomMargin = -titleBarHeight;
        coverLayout.setLayoutParams(params);
        setSupportActionBar(toolbar);
        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        singleNewsViewModel = new ViewModelProvider(this, factory)
                .get(SingleNewsViewModel.class);
        singleNewsViewModel.getNews().observe(this, this::consumeNews);
        String newsId = getIntent().getStringExtra(NEWS_ID);
        singleNewsViewModel.loadNews(newsId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.news_read_more)
    public void openLink() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(news.getUrl()));
        startActivity(intent);
    }

    private void consumeNews(News news) {
        this.news = news;
        String imageUrl = news.getImage();
        if (!imageUrl.contains("https:") || !imageUrl.contains("http:")) {
            imageUrl = "https:".concat(imageUrl);
        }
        Picasso.get().load(imageUrl)
                .centerCrop()
                .fit()
                .into(coverImage);
        coverImage.setContentDescription(news.getTitle());
        title.setText(news.getTitle());
        text.setText(HtmlCompat.fromHtml(news.getDescription(), 0));
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}