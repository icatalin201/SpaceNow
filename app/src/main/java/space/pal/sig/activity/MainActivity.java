package space.pal.sig.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

import space.pal.sig.R;
import space.pal.sig.fragment.ApodFragment;
import space.pal.sig.fragment.FactsFragment;
import space.pal.sig.fragment.FeedFragment;
import space.pal.sig.fragment.GlossaryFragment;
import space.pal.sig.fragment.ImagesFragment;
import space.pal.sig.fragment.NewsFragment;
import space.pal.sig.util.EndDrawerToggle;
import space.pal.sig.util.NotificationService;
import space.pal.sig.util.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private EndDrawerToggle endDrawerToggle;
    private boolean pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        endDrawerToggle = new EndDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(endDrawerToggle);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
            mNavigationView.getMenu().getItem(0).setChecked(true);
        }
        if (savedInstanceState == null) {
            Utils.changeFragment(this, new ApodFragment());
        }
        String notification = Utils.getFromSharedPreferences(Utils.NOTIFICATION, this, Utils.NOTIFICATION);
        String day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if (notification.equals("") || !notification.equals(day)) {
            setNotifications();
        }
    }

    private void setNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 30);
        Intent intent = new Intent(MainActivity.this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Utils.putInSharedPreferences(Utils.NOTIFICATION, this, Utils.NOTIFICATION,
                Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (pressed) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "Press again for exit.", Toast.LENGTH_SHORT).show();
                pressed = true;
            }
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState,
                             @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        endDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        Bundle args = null;
        switch (menuItem.getItemId()) {
            case R.id.apod:
                fragment = new ApodFragment();
                break;
            case R.id.images:
                fragment = new ImagesFragment();
                break;
            case R.id.facts:
                fragment = new FactsFragment();
                break;
            case R.id.news:
                fragment = new NewsFragment();
                break;
            case R.id.glossary:
                fragment = new GlossaryFragment();
                break;
            case R.id.esa_feed:
                fragment = new FeedFragment();
                args = new Bundle();
                args.putString(FeedFragment.FEED_SOURCE, Utils.ESA_FEED);
                break;
            case R.id.jwst_feed:
                fragment = new FeedFragment();
                args = new Bundle();
                args.putString(FeedFragment.FEED_SOURCE, Utils.JWST_FEED);
                break;
            case R.id.st_live_feed:
                fragment = new FeedFragment();
                args = new Bundle();
                args.putString(FeedFragment.FEED_SOURCE, Utils.ST_LIVE_FEED);
                break;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.share:
                String packageName = getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = String.format("Hi there! Try this new awesome app %s. \n",
                        getResources().getString(R.string.app_name));
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
                break;
            case R.id.rating:
                String packageName1 = getPackageName();
                String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName1;
                Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
                startActivity(app);
                break;
        }
        if (fragment != null) {
            if (args != null) {
                Utils.changeFragmentWithArguments(this, fragment, args);
            } else {
                Utils.changeFragment(this, fragment);
            }
            menuItem.setChecked(true);
        }
        pressed = false;
        drawer.closeDrawer(GravityCompat.END);
        return false;
    }
}
