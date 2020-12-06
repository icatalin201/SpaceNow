package space.pal.sig.old.view.apod;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.model.Apod;
import space.pal.sig.old.model.MediaType;
import space.pal.sig.old.view.SpaceBaseFragment;

import static space.pal.sig.old.view.apod.ImageActivity.IMAGE_ID;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodFragment extends SpaceBaseFragment implements SelectApodListener {

    @BindView(R.id.apod_image)
    AppCompatImageView image;
    @BindView(R.id.apod_video)
    WebView video;
    @BindView(R.id.apod_title)
    AppCompatTextView title;
    @BindView(R.id.apod_recycler)
    RecyclerView apodRecycler;
    @BindView(R.id.last_apods_layout)
    ConstraintLayout layout;
    @Inject
    ApodViewModelFactory factory;
    private ApodViewModel apodViewModel;
    private ApodHorizontalAdapter apodHorizontalAdapter;
    private Apod todayApod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        Space.getApplicationComponent().inject(this);
        setupBinding(this, view);
        setupHeight();
        apodHorizontalAdapter = new ApodHorizontalAdapter(this);
        apodRecycler.setAdapter(apodHorizontalAdapter);
        apodRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        apodViewModel = new ViewModelProvider(this, factory).get(ApodViewModel.class);
        apodViewModel.getTodayApod().observe(getViewLifecycleOwner(), this::consumeApod);
        apodViewModel.getApodList().observe(getViewLifecycleOwner(), this::consumeApodList);
        return view;
    }

    @Override
    public void onClick(Apod apod) {
        Intent intent = new Intent(getParentActivity(), ImageActivity.class);
        intent.putExtra(IMAGE_ID, apod.getId());
        startActivity(intent);
    }

    @OnClick(R.id.apod_image)
    public void onClickImage() {
        onClick(todayApod);
    }

    @OnClick(R.id.see_all_apods)
    public void onClickSeeAll() {
        Intent intent = new Intent(getParentActivity(), ImagesActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void consumeApod(Apod apod) {
        this.todayApod = apod;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                layout.getLayoutParams();
        if (apod.getType() == MediaType.IMAGE) {
            image.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            video.setVisibility(View.GONE);
            title.setText(apod.getTitle());
            image.setContentDescription(apod.getTitle());
            Picasso.get().load(apod.getUrl())
                    .centerCrop()
                    .fit()
                    .into(image);
            params.topToBottom = image.getId();
        } else {
            image.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            video.getSettings().setJavaScriptEnabled(true);
            video.setWebChromeClient(new WebChromeClient());
            video.loadUrl(apod.getUrl());
            params.topToBottom = video.getId();
        }
        layout.setLayoutParams(params);
    }

    private void consumeApodList(List<Apod> apodList) {
        apodHorizontalAdapter.submitList(apodList);
    }

    private void setupHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getParentActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.7);
        image.getLayoutParams().height = height;
        video.getLayoutParams().height = height;
    }
}
