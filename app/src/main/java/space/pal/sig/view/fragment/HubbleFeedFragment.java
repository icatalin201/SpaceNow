package space.pal.sig.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.view.activity.NewsActivity;
import space.pal.sig.view.adapter.NewsAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class HubbleFeedFragment extends Fragment implements NewsAdapter.NewsClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private NewsAdapter newsAdapter;
    @BindView(R.id.feeds) RecyclerView feeds;

    private int page;
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
        mainViewModel.setLoading(true);
        page = 1;
        newsAdapter = new NewsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        feeds.setLayoutManager(linearLayoutManager);
        feeds.setItemAnimator(new DefaultItemAnimator());
        feeds.setAdapter(newsAdapter);
        mainViewModel.getHubbleFeed().observe(this, newsDtos -> {
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
        mainViewModel.downloadHubble(1);
        super.onDetach();
    }

    @Override
    public void onClick(NewsDto newsDto) {
        mainViewModel.setSelectedNews(newsDto);
        startActivity(new Intent(appCompatActivity, NewsActivity.class));
    }
}
