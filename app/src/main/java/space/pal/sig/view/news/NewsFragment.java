package space.pal.sig.view.news;

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
import space.pal.sig.Space;
import space.pal.sig.model.News;
import space.pal.sig.model.NewsSource;
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
        inflater.inflate(R.menu.filter_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            String[] sources = newsViewModel.getNewsSources();
            AlertDialog dialog = new AlertDialog
                    .Builder(getParentActivity())
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

    private void onSelectSource(NewsSource source) {
        newsViewModel.findNewsBySource(source);
        getParentActivity().setTitle(source.getText());
    }

    private void consumeNewsList(PagedList<News> newsPagedList) {
        newsAdapter = new NewsAdapter();
        newsRecycler.setAdapter(newsAdapter);
        newsAdapter.submitList(newsPagedList);
    }
}
