package space.pal.sig.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jsibbold.zoomage.ZoomageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.model.Apod;
import space.pal.sig.util.IntelViewModelFactory;
import space.pal.sig.view.viewmodel.MainViewModel;

public class ApodActivity extends AppCompatActivity {

    @BindView(R.id.scroll) ScrollView content;
    @BindView(R.id.hide) ImageButton hide;
    @BindView(R.id.show) ImageButton show;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.apod_image) ZoomageView image;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @Inject IntelViewModelFactory factory;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        Space.getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
        }
        MainViewModel mainViewModel = ViewModelProviders
                .of(this, factory)
                .get(MainViewModel.class);
        Apod apod = mainViewModel.getSelectedApod();
        setupApod(apod);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.show)
    void onShow() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        animation.setDuration(300);
        content.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                content.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hide.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @OnClick(R.id.hide)
    void onHide() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
        animation.setDuration(300);
        content.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                hide.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                content.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setupApod(Apod apod) {
        description.setText(apod.getExplanation());
        Glide.with(this)
                .load(apod.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(image);
        setTitle(apod.getTitle());
    }
}
