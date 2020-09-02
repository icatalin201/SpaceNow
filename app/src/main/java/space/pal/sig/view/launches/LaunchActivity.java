package space.pal.sig.view.launches;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.Launch;
import space.pal.sig.repository.dto.LocationDto;
import space.pal.sig.repository.dto.MissionDto;
import space.pal.sig.repository.dto.RocketDto;
import space.pal.sig.view.SpaceBaseActivity;

/**
 * SpaceNow
 * Created by Catalin on 7/26/2020
 **/
public class LaunchActivity extends SpaceBaseActivity {

    public static final String LAUNCH_ID = "launch_id";

    @BindView(R.id.launch_rocket_image_cover)
    AppCompatImageView coverImage;
    @BindView(R.id.launch_title)
    AppCompatTextView title;
    @BindView(R.id.launch_favorite)
    AppCompatImageButton favorite;
    @BindView(R.id.launch_cover_layout)
    ConstraintLayout coverLayout;
    @BindView(R.id.launch_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.launch_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.launch_rocket_image)
    AppCompatImageView rocketImage;
    @BindView(R.id.days)
    AppCompatTextView days;
    @BindView(R.id.hours)
    AppCompatTextView hours;
    @BindView(R.id.minutes)
    AppCompatTextView minutes;
    @BindView(R.id.seconds)
    AppCompatTextView seconds;
    @BindView(R.id.launch_status)
    AppCompatTextView launchStatus;
    @BindView(R.id.divider2)
    View divider2;
    @BindView(R.id.launch_clock)
    LinearLayout clockLayout;
    @BindView(R.id.launch_missions_label)
    AppCompatTextView missionsLabel;
    @BindView(R.id.launch_missions_recycler)
    RecyclerView missionsRecycler;
    @BindView(R.id.launch_date)
    AppCompatTextView launchDate;
    @BindView(R.id.launch_location_name)
    AppCompatTextView locationName;
    @BindView(R.id.launch_rocket_name)
    AppCompatTextView rocketName;
    @BindView(R.id.launch_rocket_configuration)
    AppCompatTextView rocketConfiguration;
    @BindView(R.id.launch_rocket_family)
    AppCompatTextView rocketFamily;
    @Inject
    LaunchViewModelFactory factory;
    @BindView(R.id.launch_toolbar)
    Toolbar toolbar;
    private LaunchViewModel launchViewModel;
    private CountDownTimer countDownTimer;
    private LaunchMissionsAdapter launchMissionsAdapter;
    private Launch launch;
    private Menu menu;
    private long launchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Space.getApplicationComponent().inject(this);
        int titleBarHeight = getStatusBarHeight();
        CollapsingToolbarLayout.LayoutParams params =
                (CollapsingToolbarLayout.LayoutParams) coverLayout.getLayoutParams();
        params.bottomMargin = -titleBarHeight;
        coverLayout.setLayoutParams(params);
        setSupportActionBar(toolbar);
        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        launchId = getIntent().getLongExtra(LAUNCH_ID, 0);
        launchViewModel = new ViewModelProvider(this, factory).get(LaunchViewModel.class);
        launchViewModel.getLaunch().observe(this, this::consumeLaunch);
        launchViewModel.loadLaunch(launchId);
        launchMissionsAdapter = new LaunchMissionsAdapter();
        missionsRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        missionsRecycler.setAdapter(launchMissionsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        boolean hasNotifications = launchViewModel
                .hasNotifications(launchId);
        int menuId = hasNotifications ? R.menu.menu_launch_notification : R.menu.menu_launch;
        getMenuInflater().inflate(menuId, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.watch:
                if (launch.getVideoUrls() != null) {
                    final String[] videoUrls = launch.getVideoUrls().split(",");
                    Dialog dialog = new AlertDialog
                            .Builder(this, R.style.AppTheme_Dialog)
                            .setTitle(R.string.watch)
                            .setItems(videoUrls, (dialogInterface, i) -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(videoUrls[i]));
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                dialogInterface.dismiss();
                            })
                            .create();
                    dialog.show();
                }
                break;
            case R.id.notification:
                final String[] options = new String[]{"On", "Off"};
                Dialog dialog = new AlertDialog
                        .Builder(this, R.style.AppTheme_Dialog)
                        .setTitle(R.string.notification)
                        .setItems(options, (dialogInterface, i) -> {
                            launchViewModel.toggleNotifications(i == 0, launchId);
                            dialogInterface.dismiss();
                            supportInvalidateOptionsMenu();
                            onCreateOptionsMenu(menu);
                        })
                        .create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.view_location)
    public void viewLocation() {
        LocationDto locationDto = launch.getLocation();
        if (locationDto == null) return;
        double latitude = Double.parseDouble(locationDto.getPads().get(0).getLatitude());
        double longitude = Double.parseDouble(locationDto.getPads().get(0).getLongitude());
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.launch_favorite)
    public void toggleFavorite() {
        launchViewModel.toggleFavorite(launch);
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private void consumeLaunch(Launch launch) {
        this.launch = launch;
        RocketDto rocketDto = launch.getRocket();
        LocationDto locationDto = launch.getLocation();
        List<MissionDto> missionDtoList = launch.getMissions();
        if (rocketDto == null) return;
        if (launch.getFavorite()) {
            favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        title.setText(launch.getName());
        if (missionDtoList == null || missionDtoList.size() == 0) {
            missionsLabel.setVisibility(View.GONE);
            missionsRecycler.setVisibility(View.GONE);
        } else {
            launchMissionsAdapter.submitList(missionDtoList);
            missionsLabel.setVisibility(View.VISIBLE);
            missionsRecycler.setVisibility(View.VISIBLE);
        }
        launchDate.setText(launch.getDate());
        if (locationDto != null && locationDto.getPads().size() > 0) {
            locationName.setText(locationDto.getPads().get(0).getName());
        }
        Picasso.get().load(rocketDto.getImageURL())
                .centerCrop()
                .fit()
                .into(coverImage);
        Picasso.get().load(rocketDto.getImageURL())
                .centerCrop()
                .fit()
                .into(rocketImage);
        rocketName.setText(rocketDto.getName());
        rocketConfiguration.setText(String.format("Configuration: %s", rocketDto.getConfiguration()));
        if (rocketDto.getFamily() != null) {
            rocketFamily.setText(String.format("Family: %s", rocketDto.getFamily().getName()));
        }
        switch (launch.getStatus()) {
            case GREEN:
                long now = Calendar.getInstance().getTimeInMillis();
                long countDown = launch.getTimestamp() - now;
                countDownTimer = new CountDownTimer(countDown, 1000) {
                    @Override
                    public void onTick(long l) {
                        long secondsInMilli = 1000;
                        long minutesInMilli = secondsInMilli * 60;
                        long hoursInMilli = minutesInMilli * 60;
                        long daysInMilli = hoursInMilli * 24;
                        long d = l / daysInMilli;
                        l = l % daysInMilli;
                        long h = l / hoursInMilli;
                        l = l % hoursInMilli;
                        long m = l / minutesInMilli;
                        l = l % minutesInMilli;
                        long s = l / secondsInMilli;
                        String dString = formatNumber(d);
                        String hString = formatNumber(h);
                        String mString = formatNumber(m);
                        String sString = formatNumber(s);
                        if (days == null || hours == null || minutes == null || seconds == null)
                            return;
                        days.setText(dString);
                        hours.setText(hString);
                        minutes.setText(mString);
                        seconds.setText(sString);
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                countDownTimer.start();
                clockLayout.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);
                launchStatus.setBackgroundResource(R.drawable.launch_status_success);
                break;
            case SUCCESS:
                launchStatus.setBackgroundResource(R.drawable.launch_status_success);
                clockLayout.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
                break;
            case RED:
            case FAILED:
                launchStatus.setBackgroundResource(R.drawable.launch_status_failure);
                clockLayout.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
                break;
        }
        launchStatus.setText(launch.getStatus().getName());
    }

    private String formatNumber(long number) {
        String numberStr;
        if (number < 10) {
            numberStr = String.format("0%s", number);
        } else {
            numberStr = String.valueOf(number);
        }
        return numberStr;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}