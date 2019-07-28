package space.pal.sig.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.util.GlideApp;
import space.pal.sig.view.activity.NewsActivity;
import space.pal.sig.view.adapter.NewsAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class HubbleFeedFragment extends Fragment implements NewsAdapter.NewsClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private NewsAdapter newsAdapter;
    @BindView(R.id.feeds) RecyclerView feeds;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.image) ImageView image;
    private NewsDto newsDto;

    private int page = 1;
    private boolean loading = true;
    private int previousTotal = 0;

    public HubbleFeedFragment() { }

    public static HubbleFeedFragment getInstance() {
        return new HubbleFeedFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        newsAdapter = new NewsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        feeds.setLayoutManager(linearLayoutManager);
        feeds.setItemAnimator(new DefaultItemAnimator());
        feeds.setAdapter(newsAdapter);
        mainViewModel.getHubbleFeed().observe(this, newsDtos -> {
            if (newsDtos != null) {
                setupBigFeed(newsDtos.get(0));
                newsDtos.remove(0);
                newsAdapter.addItems(newsDtos);
                page++;
            }
        });
        mainViewModel.getMoreHubbleFeed().observe(this, newsDtos -> {
            if (newsDtos != null) {
                mainViewModel.setLoading(false);
                newsAdapter.addItems(newsDtos);
                page++;
            }
        });
        feeds.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                int visibleThreshold = 5;
                if (!loading && (totalItemCount - visibleItemCount) <=
                        (firstVisibleItem + visibleThreshold)) {
                    mainViewModel.setLoading(true);
                    mainViewModel.downloadHubble(page);
                    loading = true;
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(NewsDto newsDto) {
        mainViewModel.setSelectedNews(newsDto);
        startActivity(new Intent(appCompatActivity, NewsActivity.class));
    }

    @OnClick(R.id.card)
    void onClickBig() {
        onClick(this.newsDto);
    }

    private void setupBigFeed(NewsDto newsDto) {
        this.newsDto = newsDto;
        String thumbnail = newsDto.getThumbnail();
        String url = "";
        if (thumbnail != null && !thumbnail.contains("https:")) {
            url = "https:" + thumbnail;
        }
        GlideApp.with(appCompatActivity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .centerCrop()
                        .autoClone()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .error(R.drawable.ic_placeholder)
                .into(this.image);
        title.setText(newsDto.getName());
        description.setText(newsDto.getAbstractText());
    }
}
