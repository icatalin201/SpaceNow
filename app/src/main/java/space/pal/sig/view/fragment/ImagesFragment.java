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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.Apod;
import space.pal.sig.view.activity.ApodActivity;
import space.pal.sig.view.adapter.ApodAdapter;
import space.pal.sig.view.adapter.GalleryAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class ImagesFragment extends Fragment implements ApodAdapter.ApodClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private GalleryAdapter galleryAdapter;
    private MainViewModel mainViewModel;
    @BindView(R.id.images) RecyclerView images;

    public ImagesFragment() { }

    public static ImagesFragment getInstance() {
        return new ImagesFragment();
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
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        unbinder = ButterKnife.bind(this, view);
        galleryAdapter = new GalleryAdapter(this);
        images.setLayoutManager(new GridLayoutManager(appCompatActivity, 2));
        images.setItemAnimator(new DefaultItemAnimator());
        images.setAdapter(galleryAdapter);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        mainViewModel.getApods().observe(this, apods -> {
            if (apods != null) {
                galleryAdapter.add(apods);
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
        super.onDetach();
    }

    @Override
    public void onClick(Apod apod) {
        mainViewModel.setSelectedApod(apod);
        startActivity(new Intent(appCompatActivity, ApodActivity.class));
    }
}
