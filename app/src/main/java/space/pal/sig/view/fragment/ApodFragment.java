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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.Apod;
import space.pal.sig.view.activity.ApodActivity;
import space.pal.sig.view.adapter.ApodAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class ApodFragment extends Fragment implements ApodAdapter.ApodClickListener {

    private AppCompatActivity appCompatActivity;
    private MainViewModel mainViewModel;
    private Unbinder unbinder;
    private ApodAdapter apodAdapter;
    @BindView(R.id.apod_image) ImageView apodImage;
    @BindView(R.id.apod_title) TextView apodTitle;
    @BindView(R.id.apods) RecyclerView apods;

    public ApodFragment() { }

    public static ApodFragment getInstance() {
        return new ApodFragment();
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
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        apodAdapter = new ApodAdapter(this);
        apods.setAdapter(apodAdapter);
        apods.setItemAnimator(new DefaultItemAnimator());
        apods.setLayoutManager(new GridLayoutManager(appCompatActivity, 2,
                GridLayoutManager.HORIZONTAL, false));
        mainViewModel.getApod().observe(this, apod -> {
            if (apod != null) {
                RequestManager manager = Glide.with(appCompatActivity);
                manager.load(apod.getUrl())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .autoClone()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH))
                        .error(manager.load(R.drawable.ic_placeholder))
                        .into(apodImage);
                apodTitle.setText(apod.getTitle());
            }
        });
        mainViewModel.getApods().observe(this, apods -> {
            if (apods != null) {
                apodAdapter.add(apods);
                this.apods.scrollToPosition(0);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.apod)
    void onApodClick() {
        onClick(mainViewModel.getApod().getValue());
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(Apod apod) {
        mainViewModel.setSelectedApod(apod);
        startActivity(new Intent(appCompatActivity, ApodActivity.class));
    }
}
