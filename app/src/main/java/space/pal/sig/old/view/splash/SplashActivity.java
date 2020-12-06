package space.pal.sig.old.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.view.SpaceBaseActivity;
import space.pal.sig.old.view.main.MainActivity;

/**
 * SpaceNow
 * Created by Catalin on 7/22/2020
 **/
public class SplashActivity extends SpaceBaseActivity {

    @BindView(R.id.splash_progress_bar)
    ProgressBar progressBar;
    @Inject
    SplashViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Space.getApplicationComponent().inject(this);
        SplashViewModel splashViewModel = new ViewModelProvider(this, factory)
                .get(SplashViewModel.class);
        splashViewModel.isSafeForStart().observe(this, safeForStart -> {
            progressBar.setVisibility(safeForStart ? View.INVISIBLE : View.VISIBLE);
            if (safeForStart) {
                startApp();
            }
        });
    }

    private void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}