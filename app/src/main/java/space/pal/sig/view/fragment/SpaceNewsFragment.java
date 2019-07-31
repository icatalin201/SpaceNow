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
import space.pal.sig.model.dto.SpaceFlightNewsDto;
import space.pal.sig.view.activity.WebViewActivity;
import space.pal.sig.view.adapter.SpaceNewsAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;

public class SpaceNewsFragment extends Fragment implements SpaceNewsAdapter.SpaceNewsClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private SpaceNewsAdapter spaceNewsAdapter;
    private MainViewModel mainViewModel;
    @BindView(R.id.space_news_recycler) RecyclerView spaceNewsRecycler;

    private int page;
    private boolean loading = true;
    private int previousTotal = 0;

    public SpaceNewsFragment() { }

    public static SpaceNewsFragment getInstance() {
        return new SpaceNewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_space_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        mainViewModel.setLoading(true);
        page = 1;
        spaceNewsAdapter = new SpaceNewsAdapter(this);
        spaceNewsRecycler.setHasFixedSize(true);
        spaceNewsRecycler.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(appCompatActivity);
        spaceNewsRecycler.setLayoutManager(manager);
        spaceNewsRecycler.setAdapter(spaceNewsAdapter);
        mainViewModel.getSpaceFlightNews().observe(this, spaceFlightNewsDtos -> {
            if (spaceFlightNewsDtos != null) {
                mainViewModel.setLoading(false);
                spaceNewsAdapter.add(spaceFlightNewsDtos);
                page++;
            }
        });
        spaceNewsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();
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
                    mainViewModel.downloadSpaceFlightNews(page);
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
        mainViewModel.downloadSpaceFlightNews(1);
        super.onDetach();
    }

    @Override
    public void onClick(SpaceFlightNewsDto spaceFlightNewsDto) {
        Intent intent = new Intent(appCompatActivity, WebViewActivity.class);
        intent.putExtra(TITLE, spaceFlightNewsDto.getTitle());
        intent.putExtra(URL, spaceFlightNewsDto.getUrl());
        startActivity(intent);
    }
}
