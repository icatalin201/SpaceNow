package space.pal.sig.old.view;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * SpaceNow
 * Created by Catalin on 7/17/2020
 **/
public class SpaceBaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    protected void setupBinding(SpaceBaseActivity activity) {
        unbinder = ButterKnife.bind(activity);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupBinding(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
