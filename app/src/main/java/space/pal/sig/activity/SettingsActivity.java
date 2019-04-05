package space.pal.sig.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.util.Utils;

public class SettingsActivity extends AppCompatActivity {

    private Switch quality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        quality = findViewById(R.id.hd_switch);
        String hd = Utils.getFromSharedPreferences(Utils.QUALITY_HD, this, Utils.QUALITY_HD);
        boolean checked = hd.equals("") || hd.equals("no");
        quality.setChecked(!checked);
    }

    private void savePref() {
        if (quality.isChecked()) {
            Utils.putInSharedPreferences(Utils.QUALITY_HD, this, Utils.QUALITY_HD, "yes");
        } else {
            Utils.putInSharedPreferences(Utils.QUALITY_HD, this, Utils.QUALITY_HD, "no");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        savePref();
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        savePref();
        super.onBackPressed();
    }
}
