package space.pal.sig.view.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.view.SpaceBaseActivity;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class WebActivity extends SpaceBaseActivity {

    public static final String SPACE_URL = "url";

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String url = getIntent().getStringExtra(SPACE_URL);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}