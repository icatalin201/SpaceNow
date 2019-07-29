package space.pal.sig.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.dto.LaunchDto;
import space.pal.sig.model.dto.MissionDto;
import space.pal.sig.model.dto.PadDto;
import space.pal.sig.model.dto.RocketDto;
import space.pal.sig.util.IntelViewModelFactory;
import space.pal.sig.view.adapter.MissionsAdapter;
import space.pal.sig.view.fragment.MediaLinkDialog;
import space.pal.sig.view.viewmodel.MainViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;
import static space.pal.sig.util.DateTimeUtil.formatNumber;
import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;


public class LaunchActivity extends AppCompatActivity
        implements MissionsAdapter.MissionClickListener {

    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private MissionsAdapter missionsAdapter;
    private CountDownTimer countDownTimer;
    @Inject IntelViewModelFactory factory;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.rocket_image) ImageView rocketImage;
    @BindView(R.id.days) TextView days;
    @BindView(R.id.hours) TextView hours;
    @BindView(R.id.minutes) TextView minutes;
    @BindView(R.id.seconds) TextView seconds;
    @BindView(R.id.launch_status) TextView launchStatus;
    @BindView(R.id.card_status) CardView cardStatus;
    @BindView(R.id.divider2) View divider2;
    @BindView(R.id.divider3) View divider3;
    @BindView(R.id.clock) LinearLayout clockLayout;
    @BindView(R.id.watch) Button watch;
    @BindView(R.id.missions) TextView missionsLabel;
    @BindView(R.id.missions_recycler) RecyclerView missionsRecycler;
    @BindView(R.id.date) TextView launchDate;
    @BindView(R.id.location_name) TextView locationName;
    @BindView(R.id.layout) ConstraintLayout layout;
    @BindView(R.id.rocket_image_2) ImageView rocketImage2;
    @BindView(R.id.rocket_name) TextView rocketName;
    @BindView(R.id.rocket_configuration) TextView rocketConfiguration;
    @BindView(R.id.rocket_family) TextView rocketFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Space.getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        setupLayout();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        missionsAdapter = new MissionsAdapter(this);
        missionsRecycler.setItemAnimator(new DefaultItemAnimator());
        missionsRecycler.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
        missionsRecycler.setAdapter(missionsAdapter);
        LaunchDto launchDto = mainViewModel.getSelectedLaunch();
        setupView(launchDto);
        if (launchDto.getName().contains("|")) {
            collapsingToolbarLayout.setTitle(launchDto.getName().split("\\|")[1]);
        } else {
            collapsingToolbarLayout.setTitle(launchDto.getName());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.watch)
    void onWatch() {
        MediaLinkDialog.getInstance().show(getSupportFragmentManager(), "media");
    }

    private void setupView(LaunchDto launchDto) {
        RocketDto rocket = launchDto.getRocket();
        mainViewModel.downloadRocket(rocket.getId());
        List<PadDto> padDtoList = launchDto.getLocation().getPads();
        List<MissionDto> missionDtos = launchDto.getMissions();
        Glide.with(this)
                .load(rocket.getImageURL())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(rocketImage);
        Glide.with(this)
                .load(rocket.getImageURL())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(rocketImage2);
        String status = "";
        int color = 0;
        switch (launchDto.getStatus()) {
            case 1:
                status = "Available for launch";
                color = getResources().getColor(android.R.color.holo_green_dark);
                long now = Calendar.getInstance().getTimeInMillis();
                long countDown = launchDto.getNetstamp() * 1000 - now;
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
                        days.setText(dString);
                        hours.setText(hString);
                        minutes.setText(mString);
                        seconds.setText(sString);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                clockLayout.setVisibility(VISIBLE);
                divider2.setVisibility(VISIBLE);
                break;
            case 2:
                status = "Not available for launch";
                color = getResources().getColor(android.R.color.holo_red_dark);
                break;
            case 3:
                status = "Successfully launched";
                color = getResources().getColor(android.R.color.holo_green_dark);
                break;
            case 4:
                status = "Launch failed";
                color = getResources().getColor(android.R.color.holo_red_dark);
                break;
        }
        launchStatus.setText(status);
        if (color != 0) {
            cardStatus.setCardBackgroundColor(color);
        }
        if (launchDto.getVidURLs().length > 0) {
            watch.setVisibility(VISIBLE);
            divider3.setVisibility(VISIBLE);
        } else {
            watch.setVisibility(GONE);
            divider3.setVisibility(GONE);
        }
        launchDate.setText(launchDto.getNet());
        locationName.setText(padDtoList.get(0).getName());
        missionsAdapter.add(missionDtos);
        if (missionDtos != null && missionDtos.size() > 0) {
            missionsLabel.setVisibility(VISIBLE);
            missionsRecycler.setVisibility(VISIBLE);
        } else {
            missionsLabel.setVisibility(GONE);
            missionsRecycler.setVisibility(GONE);
        }
        mainViewModel.getRocket().observe(this, rocketDto -> {
            if (rocketDto != null) {
                rocketName.setText(rocketDto.getName());
                rocketConfiguration.setText(String.format("Configuration: %s", rocketDto.getConfiguration()));
                if (rocketDto.getFamily() != null) {
                    rocketFamily.setText(String.format("Family: %s", rocketDto.getFamily().getName()));
                }
            }
        });
    }

    @Override
    public void onMissionClick(MissionDto missionDto) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(TITLE, missionDto.getName());
        intent.putExtra(URL, missionDto.getWikiURL());
        startActivity(intent);
    }

    @OnClick(R.id.rocket_item)
    public void onRocketClick() {
        RocketDto rocketDto = mainViewModel.getSelectedLaunch().getRocket();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(TITLE, rocketDto.getName());
        intent.putExtra(URL, rocketDto.getWikiURL());
        startActivity(intent);
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

    private void setupLayout() {
        int titleBarHeight = getStatusBarHeight();
        CollapsingToolbarLayout.LayoutParams params =
                (CollapsingToolbarLayout.LayoutParams) layout.getLayoutParams();
        params.bottomMargin = -titleBarHeight;
        layout.setLayoutParams(params);
    }
}
