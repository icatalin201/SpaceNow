package space.pal.sig.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.Unbinder;
import space.pal.sig.R;

public class SplashActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.app_name) TextView appName;
    @BindView(R.id.app_name_extended) TextView appNameExtended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animateView(appName);
        animateViewWithDelay(appNameExtended);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
