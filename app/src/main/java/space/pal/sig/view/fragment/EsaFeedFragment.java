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
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.util.GlideApp;
import space.pal.sig.view.activity.FeedActivity;
import space.pal.sig.view.adapter.FeedAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class EsaFeedFragment extends Fragment implements FeedAdapter.FeedClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private FeedAdapter feedAdapter;
    @BindView(R.id.feeds) RecyclerView feeds;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.image) ImageView image;
    private FeedDto feedDto;

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
        feedAdapter = new FeedAdapter(this);
        feeds.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        feeds.setItemAnimator(new DefaultItemAnimator());
        feeds.setAdapter(feedAdapter);
        mainViewModel.getEsaFeed().observe(this, feedDtos -> {
            if (feedDtos != null) {
                setupBigFeed(feedDtos.get(0));
                feedDtos.remove(0);
                feedAdapter.addItems(feedDtos);
            }
        });
        return view;
    }

    @OnClick(R.id.card)
    void onClickBig() {
        onClick(this.feedDto);
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
    public void onClick(FeedDto feedDto) {
        mainViewModel.setSelectedFeed(feedDto);
        startActivity(new Intent(appCompatActivity, FeedActivity.class));
    }

    private void setupBigFeed(FeedDto feedDto) {
        this.feedDto = feedDto;
        String thumbnail = feedDto.getThumbnail();
        String image = feedDto.getImage();
        String url = "";
        if (thumbnail != null && !thumbnail.contains("https:")) {
            url = "https:" + thumbnail;
        } else if (image != null && !image.contains("https:")) {
            url = "https:" + image;
        }
        GlideApp.with(appCompatActivity)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .centerCrop()
                        .autoClone()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(this.image);
        title.setText(feedDto.getTitle());
        description.setText(feedDto.getDescription());
    }
}
