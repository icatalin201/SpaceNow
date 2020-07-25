package space.pal.sig.view.launches;

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

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.Launch;
import space.pal.sig.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/18/2020
 **/
public class LaunchesFragment extends SpaceBaseFragment {

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
        launchesAdapter = new LaunchesAdapter();
        launchesRecycler.setAdapter(launchesAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        launchesRecycler.setLayoutManager(manager);
        viewModel = new ViewModelProvider(this, factory).get(LaunchesViewModel.class);
        viewModel.getLaunchList().observe(getViewLifecycleOwner(), this::consumeLaunchList);
        viewModel.getSelectedFilter().observe(getViewLifecycleOwner(), this::onSelectFilter);
        launchesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = manager.findFirstCompletelyVisibleItemPosition();
                Launch launch = launchesAdapter.getLaunch(position);
                Date date = new Date(launch.getTimestamp());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String title = getParentActivity().getTitle().toString().split(":")[0];
                title = title.concat(": ")
                        .concat(String.valueOf(calendar.get(Calendar.YEAR)));
                getParentActivity().setTitle(title);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            String[] launches = viewModel.getFilterLaunches();
            AlertDialog dialog = new AlertDialog
                    .Builder(getParentActivity())
                    .setItems(launches, (d, w) -> {
                        viewModel.selectFilter(w);
                        d.dismiss();
                    })
                    .create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSelectFilter(int pos) {
        viewModel.filter(pos);
        String title = "Future Launches";
        if (pos != 0) {
            title = "Past Launches";
        }
        title = title.concat(": ")
                .concat(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        getParentActivity().setTitle(title);
    }

    private void consumeLaunchList(PagedList<Launch> launches) {
        launchesRecycler.invalidate();
        launchesAdapter.submitList(launches);
    }

}
