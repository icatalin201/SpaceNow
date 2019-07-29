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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.dto.LaunchDto;
import space.pal.sig.view.activity.LaunchActivity;
import space.pal.sig.view.adapter.RocketLaunchesAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class RocketLaunchesFragment extends Fragment implements RocketLaunchesAdapter.RocketLaunchClickListener {

    private Unbinder unbinder;
    private AppCompatActivity appCompatActivity;
    private RocketLaunchesAdapter rocketLaunchesAdapter;
    private MainViewModel mainViewModel;
    @BindView(R.id.rocket_launches_recycler) RecyclerView rocketLaunchesRecycler;

    public RocketLaunchesFragment() { }

    public static RocketLaunchesFragment getInstance() {
        return new RocketLaunchesFragment();
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
        View view = inflater.inflate(R.layout.fragment_launches, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        rocketLaunchesAdapter = new RocketLaunchesAdapter(this);
        rocketLaunchesRecycler.setAdapter(rocketLaunchesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(appCompatActivity);
        rocketLaunchesRecycler.setLayoutManager(layoutManager);
        rocketLaunchesRecycler.setHasFixedSize(true);
        rocketLaunchesRecycler.setItemAnimator(new DefaultItemAnimator());
        mainViewModel.getLaunches().observe(this, launchDtos -> {
            if (launchDtos != null) {
                rocketLaunchesAdapter.add(launchDtos);
                rocketLaunchesRecycler.scrollToPosition(0);
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
    public void onRocketLaunchClick(LaunchDto launchDto) {
        mainViewModel.setSelectedLaunch(launchDto);
        Intent intent = new Intent(appCompatActivity, LaunchActivity.class);
        startActivity(intent);
    }
}
