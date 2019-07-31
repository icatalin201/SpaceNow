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
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.view.activity.FeedActivity;
import space.pal.sig.view.adapter.FeedAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class EsaFeedFragment extends Fragment implements FeedAdapter.FeedClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private FeedAdapter feedAdapter;
    @BindView(R.id.feeds) RecyclerView feeds;

    private int page;
    private boolean loading = true;
    private int previousTotal = 0;

    public EsaFeedFragment() { }

    public static EsaFeedFragment getInstance() {
        return new EsaFeedFragment();
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
        feedAdapter = new FeedAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        feeds.setLayoutManager(linearLayoutManager);
        feeds.setItemAnimator(new DefaultItemAnimator());
        feeds.setAdapter(feedAdapter);
        mainViewModel.getEsaFeed().observe(this, feedDtos -> {
            if (feedDtos != null) {
                mainViewModel.setLoading(false);
                feedAdapter.addItems(feedDtos);
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
                    mainViewModel.downloadEsaFeed(page);
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
        mainViewModel.downloadEsaFeed(1);
        super.onDetach();
    }

    @Override
    public void onClick(FeedDto feedDto) {
        mainViewModel.setSelectedFeed(feedDto);
        startActivity(new Intent(appCompatActivity, FeedActivity.class));
    }
}
