package space.pal.sig.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.Space;
import space.pal.sig.repository.ApodRepository;
import space.pal.sig.repository.FactRepository;
import space.pal.sig.repository.GlossaryRepository;
import space.pal.sig.repository.LaunchRepository;
import space.pal.sig.util.DownloadListener;
import space.pal.sig.util.Shared;

public class SplashActivity extends AppCompatActivity implements DownloadListener {

    private Unbinder unbinder;

    @BindView(R.id.app_name) TextView appName;
    @BindView(R.id.app_name_extended) TextView appNameExtended;

    @Inject ApodRepository apodRepository;
    @Inject FactRepository factRepository;
    @Inject GlossaryRepository glossaryRepository;
    @Inject
    LaunchRepository launchRepository;

    private int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Space.getApplicationComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        animateView(appName);
        animateViewWithDelay(appNameExtended);
        new Handler().postDelayed(() -> {
            boolean isFirstTime = Shared.getInstance().isFirstTime();
            if (isFirstTime) {
                download();
            } else {
                finishHere();
            }
        }, 700);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onStepCompleted() {
        synchronized(this) {
            step++;
            if (step == 4) {
                finishHere();
            }
        }
    }

    private void finishHere() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        Shared.getInstance().setInitial();
    }

    private void download() {
        apodRepository.downloadAll(this);
        factRepository.download(this);
        glossaryRepository.download(this);
        launchRepository.downloadRockets(this);
    }

    private void animateView(View view) {
        ObjectAnimator move = ObjectAnimator.ofFloat(view, "translationY", -70f);
        move.setDuration(500);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1);
        alpha.setDuration(700);
        AnimatorSet animset = new AnimatorSet();
        animset.play(alpha).with(move);
        animset.start();
    }

    private void animateViewWithDelay(View view) {
        ObjectAnimator move = ObjectAnimator.ofFloat(view, "translationY", -70f);
        move.setDuration(500);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1);
        alpha.setDuration(700);
        AnimatorSet animset = new AnimatorSet();
        animset.play(alpha).with(move);
        animset.start();
        animset.setStartDelay(300);
    }
}
