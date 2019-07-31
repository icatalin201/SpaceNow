package space.pal.sig.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.NavigationItem;
import space.pal.sig.util.IntelViewModelFactory;
import space.pal.sig.util.NetworkListener;
import space.pal.sig.view.adapter.NavigationAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.core.view.GravityCompat.START;
import static com.google.android.material.snackbar.Snackbar.LENGTH_LONG;
import static space.pal.sig.BuildConfig.VERSION_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationAdapter.OnNavigationItemClickListener {

    private Unbinder unbinder;
    @BindView(R.id.appversion) TextView appVersion;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.menu_items) RecyclerView menuItems;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.banner) ImageView banner;
    @BindView(R.id.progress_horizontal) SmoothProgressBar progressBar;
    @BindView(R.id.coordinator) CoordinatorLayout coordinator;
    private ActionBarDrawerToggle toggle;
    private NavigationAdapter navigationAdapter;
    private MainViewModel mainViewModel;
    private Snackbar snackbar;
    @Inject IntelViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Space.getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        snackbar = Snackbar.make(coordinator, R.string.not_connected, LENGTH_LONG);
        snackbar.setAction(R.string.ok, v -> snackbar.dismiss());
        navigationAdapter = new NavigationAdapter(this);
        menuItems.setHasFixedSize(true);
        menuItems.setLayoutManager(new LinearLayoutManager(this));
        menuItems.setAdapter(navigationAdapter);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        appVersion.setText(String.format("%s: %s", getString(R.string.app_version), VERSION_NAME));
        mainViewModel.getFragment().observe(this, this::consumeFragment);
        mainViewModel.getNavigation().observe(this, this::consumeNavigation);
        mainViewModel.getTitle().observe(this, this::consumeTitle);
        Glide.with(this)
                .load(R.drawable.banner)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(banner);
        mainViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? VISIBLE : GONE);
        });
        mainViewModel.getIsNetworkConnected().observe(this, isConnected -> {
            if (!isConnected) snackbar.show();
            else if (snackbar.isShown()) snackbar.dismiss();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mainViewModel.dispose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(START)) {
            drawerLayout.closeDrawer(START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onNavigateFragment(NavigationItem navigationItem) {
        Boolean connected = mainViewModel.getIsNetworkConnected().getValue();
        if (connected != null && !connected) {
            snackbar.show();
        } else {
            mainViewModel.setFragment(navigationItem.getFragment());
            mainViewModel.setTitle(navigationItem.getTitle());
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public void onNavigateActivity(NavigationItem navigationItem) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void onNavigateExit() {
        finish();
    }

    private void consumeTitle(Integer s) {
        if (s != null) {
            title.setText(getString(s));
        }
    }

    private void consumeNavigation(List<NavigationItem> navigationItems) {
        if (navigationItems != null && navigationItems.size() > 0) {
            navigationAdapter.add(navigationItems);
            onNavigateFragment(navigationItems.get(0));
        }
    }

    private void consumeFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mainViewModel.setNetwork(NetworkListener.isNetworkConnected(context));
        }
    };

}
