package space.pal.sig.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;

import static space.pal.sig.BuildConfig.VERSION_NAME;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.appversion) TextView appVersion;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24px);
        }
        setTitle(R.string.about);
        appVersion.setText(String.format("%s: %s", getString(R.string.app_version), VERSION_NAME));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.privacy)
    void onClickPrivacy() {

    }

    @OnClick(R.id.share)
    void onClickShare() {
        share();
    }

    @OnClick(R.id.review)
    void onClickReview() {
        rate();
    }

    private void share() {
        String packageName = getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = String.format("Hy there! Try this awesome space app - %s.",
                getResources().getString(R.string.app_name));
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(appShareIntent);
    }

    private void rate() {
        String packageName = getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        startActivity(app);
    }
}
