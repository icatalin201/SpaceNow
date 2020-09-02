package space.pal.sig.view.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.OnClick;
import space.pal.sig.BuildConfig;
import space.pal.sig.R;
import space.pal.sig.view.SpaceBaseFragment;
import space.pal.sig.view.web.WebActivity;

import static space.pal.sig.view.web.WebActivity.SPACE_URL;

/**
 * SpaceNow
 * Created by Catalin on 8/1/2020
 **/
public class AboutFragment extends SpaceBaseFragment {

    private static final String POLICY_URL = "https://icatalin201.github.io/space/privacy_policy.html";

    @BindView(R.id.about_version)
    AppCompatTextView version;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        setupBinding(this, view);
        String version = String.format("version: %s", BuildConfig.VERSION_NAME);
        this.version.setText(version);
        return view;
    }

    @OnClick(R.id.about_privacy)
    public void openPrivacyPolicy() {
        Intent intent = new Intent(getParentActivity(), WebActivity.class);
        intent.putExtra(SPACE_URL, POLICY_URL);
        startActivity(intent);
    }

    @OnClick(R.id.about_share)
    public void share() {
        String packageName = getParentActivity().getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = String.format("Hey there! Try this awesome space app - %s.",
                getResources().getString(R.string.app_name));
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(appShareIntent);
    }

    @OnClick(R.id.about_review)
    public void giveReview() {
        String packageName = getParentActivity().getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        startActivity(app);
    }
}
