package space.pal.sig.view.apod;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import space.pal.sig.R;
import space.pal.sig.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
public class ApodFragment extends SpaceBaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        setupBinding(this, view);
        return view;
    }
}
