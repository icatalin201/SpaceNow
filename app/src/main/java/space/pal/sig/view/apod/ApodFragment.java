package space.pal.sig.view.apod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
import space.pal.sig.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodFragment extends SpaceBaseFragment {

    @BindView(R.id.apod_image)
    AppCompatImageView image;
    @BindView(R.id.apod_title)
    AppCompatTextView title;
    @BindView(R.id.apod_recycler)
    RecyclerView apodRecycler;
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

    private void consumeApod(Apod apod) {
        title.setText(apod.getTitle());
        image.setContentDescription(apod.getTitle());
        Picasso.get().load(apod.getUrl())
                .centerCrop()
                .fit()
                .into(image);
    }

    private void consumeApodList(List<Apod> apodList) {
        apodHorizontalAdapter.submitList(apodList);
    }
}
