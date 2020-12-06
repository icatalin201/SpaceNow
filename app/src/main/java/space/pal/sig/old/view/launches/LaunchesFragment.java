package space.pal.sig.old.view.launches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.model.Launch;
import space.pal.sig.old.view.SpaceBaseFragment;

import static space.pal.sig.old.view.launches.LaunchActivity.LAUNCH_ID;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
public class LaunchesFragment extends SpaceBaseFragment implements SelectLaunchListener {

    @BindView(R.id.launches_recycler)
    RecyclerView launchesRecycler;
    @Inject
    LaunchesViewModelFactory factory;
    private LaunchesViewModel viewModel;
    private LaunchesAdapter launchesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launches, container, false);
        setHasOptionsMenu(true);
        setupBinding(this, view);
        Space.getApplicationComponent().inject(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        launchesRecycler.setLayoutManager(manager);
        viewModel = new ViewModelProvider(this, factory).get(LaunchesViewModel.class);
        viewModel.getLaunchList().observe(getViewLifecycleOwner(), this::consumeLaunchList);
        viewModel.getSelectedFilter().observe(getViewLifecycleOwner(), this::onSelectFilter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            String[] launches = viewModel.getFilterLaunches();
            AlertDialog dialog = new AlertDialog
                    .Builder(getParentActivity(), R.style.AppTheme_Dialog)
                    .setItems(launches, (d, w) -> {
                        viewModel.selectFilter(w);
                        d.dismiss();
                    })
                    .create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Launch launch) {
        Intent intent = new Intent(getParentActivity(), LaunchActivity.class);
        intent.putExtra(LAUNCH_ID, launch.getId());
        startActivity(intent);
    }

    private void onSelectFilter(int pos) {
        viewModel.filter(pos);
        String title = viewModel.getFilterLaunches()[pos];
        getParentActivity().setTitle(title);
    }

    private void consumeLaunchList(PagedList<Launch> launches) {
        launchesAdapter = new LaunchesAdapter(this);
        launchesRecycler.setAdapter(launchesAdapter);
        launchesAdapter.submitList(launches);
    }
}
