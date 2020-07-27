package space.pal.sig.view.apod;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

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
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.Apod;
import space.pal.sig.model.MediaType;
import space.pal.sig.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodFragment extends SpaceBaseFragment {

    @BindView(R.id.apod_image)
    AppCompatImageView image;
    @BindView(R.id.apod_video)
    WebView video;
    @BindView(R.id.apod_title)
    AppCompatTextView title;
    @BindView(R.id.apod_recycler)
    RecyclerView apodRecycler;
    @BindView(R.id.last_apods_layout)
    LinearLayout layout;
    @Inject
    ApodViewModelFactory factory;
    private ApodViewModel apodViewModel;
    private ApodHorizontalAdapter apodHorizontalAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        Space.getApplicationComponent().inject(this);
        setupBinding(this, view);
        apodHorizontalAdapter = new ApodHorizontalAdapter();
        apodRecycler.setAdapter(apodHorizontalAdapter);
        apodRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        apodViewModel = new ViewModelProvider(this, factory).get(ApodViewModel.class);
        apodViewModel.getTodayApod().observe(getViewLifecycleOwner(), this::consumeApod);
        apodViewModel.getApodList().observe(getViewLifecycleOwner(), this::consumeApodList);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void consumeApod(Apod apod) {
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
}
