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
import space.pal.sig.model.Rocket;
import space.pal.sig.view.activity.WebViewActivity;
import space.pal.sig.view.adapter.RocketsAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;

public class RocketsFragment extends Fragment implements RocketsAdapter.RocketClickListener {

    private Unbinder unbinder;
    private AppCompatActivity appCompatActivity;
    private RocketsAdapter rocketsAdapter;
    @BindView(R.id.rockets_recycler) RecyclerView rocketsRecycler;

    public RocketsFragment() { }

    public static RocketsFragment getInstance() {
        return new RocketsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rockets, container, false);
        unbinder = ButterKnife.bind(this, view);
        MainViewModel mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        rocketsAdapter = new RocketsAdapter(this);
        rocketsRecycler.setAdapter(rocketsAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(appCompatActivity, 2);
        rocketsRecycler.setLayoutManager(layoutManager);
        rocketsRecycler.setItemAnimator(new DefaultItemAnimator());
        mainViewModel.getRockets().observe(this, rocketDtos -> {
            if (rocketDtos != null) {
                rocketsAdapter.add(rocketDtos);
                rocketsRecycler.scrollToPosition(0);
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
    public void onRocketClick(Rocket rocket) {
        Intent intent = new Intent(appCompatActivity, WebViewActivity.class);
        intent.putExtra(TITLE, rocket.getName());
        intent.putExtra(URL, rocket.getWikiURL());
        startActivity(intent);
    }
}
