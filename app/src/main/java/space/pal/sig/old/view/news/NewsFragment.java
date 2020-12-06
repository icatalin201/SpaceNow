package space.pal.sig.old.view.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.model.News;
import space.pal.sig.old.model.NewsSource;
import space.pal.sig.old.view.SpaceBaseFragment;

import static space.pal.sig.old.view.news.NewsActivity.NEWS_ID;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class NewsFragment extends SpaceBaseFragment implements SelectNewsListener {

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
        setHasOptionsMenu(true);
        setupBinding(this, view);
        Space.getApplicationComponent().inject(this);
        newsViewModel = new ViewModelProvider(this, factory)
                .get(NewsViewModel.class);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNewsList()
                .observe(getViewLifecycleOwner(), this::consumeNewsList);
        newsViewModel.getSelectedSource().observe(getViewLifecycleOwner(), this::onSelectSource);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            String[] sources = newsViewModel.getNewsSources();
            AlertDialog dialog = new AlertDialog
                    .Builder(getParentActivity(), R.style.AppTheme_Dialog)
                    .setTitle(R.string.news_sources)
                    .setItems(sources, (d, w) -> {
                        newsViewModel.setSource(sources[w]);
                        d.dismiss();
                    })
                    .create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(News news) {
        Intent intent = new Intent(getParentActivity(), NewsActivity.class);
        intent.putExtra(NEWS_ID, news.getId());
        startActivity(intent);
    }

    private void onSelectSource(NewsSource source) {
        newsViewModel.findNewsBySource(source);
        getParentActivity().setTitle(source.getText());
    }

    private void consumeNewsList(PagedList<News> newsPagedList) {
        newsAdapter = new NewsAdapter(this);
        newsRecycler.setAdapter(newsAdapter);
        newsAdapter.submitList(newsPagedList);
    }
}
