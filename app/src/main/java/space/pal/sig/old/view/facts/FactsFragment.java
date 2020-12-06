package space.pal.sig.old.view.facts;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.model.Fact;
import space.pal.sig.old.view.SpaceBaseFragment;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
public class FactsFragment extends SpaceBaseFragment {

    @BindView(R.id.fact_text)
    AppCompatTextView text;
    @BindView(R.id.facts_layout)
    ConstraintLayout layout;
    @Inject
    FactsViewModelFactory factory;
    private final List<Fact> factList = new ArrayList<>();
    private final SecureRandom secureRandom = new SecureRandom();
    private FactsViewModel factsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        setHasOptionsMenu(true);
        setupBinding(this, view);
        Space.getApplicationComponent().inject(this);
        ActionBar actionBar = getParentActivity().getSupportActionBar();
        if (actionBar != null) {
            int actionBarHeight = actionBar.getHeight();
            int statusBarHeight = getStatusBarHeight();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getParentActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            layout.getLayoutParams().height = displayMetrics.heightPixels - actionBarHeight - statusBarHeight;
        }
        factsViewModel = new ViewModelProvider(this, factory).get(FactsViewModel.class);
        factsViewModel.getFacts().observe(getViewLifecycleOwner(), this::consumeFacts);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_facts, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String packageName = getParentActivity().getPackageName();
            Intent appShareIntent = new Intent(Intent.ACTION_SEND);
            appShareIntent.setType("text/plain");
            String extraText = String.format("Did you know this? - %s. Learn more: %s",
                    text.getText(),
                    getResources().getString(R.string.app_name));
            extraText += "https://play.google.com/store/apps/details?id=" + packageName;
            appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            startActivity(appShareIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fact_next)
    public void nextFact() {
        selectFact();
    }

    private void consumeFacts(List<Fact> factList) {
        this.factList.clear();
        this.factList.addAll(factList);
        selectFact();
    }

    private void selectFact() {
        Fact fact = factList.get(secureRandom.nextInt(factList.size()));
        text.setText(fact.getName());
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
