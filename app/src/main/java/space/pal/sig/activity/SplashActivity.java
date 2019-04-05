package space.pal.sig.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.model.Apod;
import space.pal.sig.model.Facts;
import space.pal.sig.util.SqlService;
import space.pal.sig.util.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Full);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView appName = findViewById(R.id.app_name);
        TextView appNameExtended = findViewById(R.id.app_name_extended);
        animateView(appName);
        animateViewWithDelay(appNameExtended);
        String firstTime = Utils.getFromSharedPreferences(Utils.FIRST_TIME_FLAG, this, Utils.FIRST_TIME_FLAG);
        if (firstTime.equals("")) {
            Utils.putInSharedPreferences(Utils.FIRST_TIME_FLAG, this, Utils.FIRST_TIME_FLAG, "no");
            new DoDirtyJob().execute();
        } else {
            start();
        }
    }

    private void start() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 1000);
    }

    @SuppressLint("StaticFieldLeak")
    private class DoDirtyJob extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            openAndReadApod();
            openAndReadFacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            start();
        }
    }

    private void openAndReadFacts() {
        List<Facts> factsList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("doc.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                Facts facts = new Facts();
                facts.setFact(mLine);
                factsList.add(facts);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SqlService.insertFactList(this, factsList);
    }

    private void openAndReadApod() {
        List<Apod> apodList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("apod.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] result = mLine.split("-//-");
                Apod apod = new Apod();
                apod.setTitle(result[0]);
                apod.setExplanation(result[1]);
                apod.setHdurl(result[2]);
                apod.setUrl(result[3]);
                apod.setCopyright(result[4]);
                apodList.add(apod);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SqlService.insertApodList(this, apodList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
