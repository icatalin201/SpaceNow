package space.pal.sig.view.apod;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.Apod;
import space.pal.sig.view.SpaceBaseActivity;

import static space.pal.sig.view.apod.ImageActivity.IMAGE_ID;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class ImagesActivity extends SpaceBaseActivity implements SelectApodListener {

    @Inject
    ImagesViewModelFactory factory;
    @BindView(R.id.apods_recycler)
    RecyclerView apodsRecycler;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ImagesViewModel imagesViewModel;
    private ApodVerticalAdapter apodVerticalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Space.getApplicationComponent().inject(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        apodVerticalAdapter = new ApodVerticalAdapter(this);
        apodsRecycler.setAdapter(apodVerticalAdapter);
        apodsRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        imagesViewModel = new ViewModelProvider(this, factory).get(ImagesViewModel.class);
        imagesViewModel.getApodList().observe(this, this::consumeApods);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(Apod apod) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(IMAGE_ID, apod.getId());
        startActivity(intent);
    }

    private void consumeApods(PagedList<Apod> apods) {
        apodVerticalAdapter.submitList(apods);
    }
}