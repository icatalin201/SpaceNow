package space.pal.sig.view.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.News;
import space.pal.sig.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class NewsFragment extends SpaceBaseFragment {

    @BindView(R.id.news_recycler)
    RecyclerView newsRecycler;
    @Inject
    NewsViewModelFactory factory;
    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,
                container, false);
        setupBinding(this, view);
        Space.getApplicationComponent().inject(this);
        newsViewModel = new ViewModelProvider(this, factory)
                .get(NewsViewModel.class);
        newsAdapter = new NewsAdapter();
        newsRecycler.setAdapter(newsAdapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNewsList()
                .observe(getViewLifecycleOwner(), this::consumeNewsList);
        return view;
    }

    private void consumeNewsList(PagedList<News> newsPagedList) {
        newsAdapter.submitList(newsPagedList);
    }
}
