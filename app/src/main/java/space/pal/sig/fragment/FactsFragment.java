package space.pal.sig.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.model.Facts;
import space.pal.sig.util.SqlService;
import space.pal.sig.util.Utils;

public class FactsFragment extends Fragment {

    private List<Facts> factsList;
    private TextView fact;
    private int index = 0;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        setHasOptionsMenu(true);
        fact = view.findViewById(R.id.fact);
        ImageButton left = view.findViewById(R.id.left);
        ImageButton right = view.findViewById(R.id.right);
        RelativeLayout content = view.findViewById(R.id.content);
        RelativeLayout noContent = view.findViewById(R.id.no_content);
        context = getActivity();
        if (context != null) {
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar()).setTitle(R.string.facts);
            factsList = SqlService.getFacts(context);
            if (factsList != null && factsList.size() > 0) {
                noContent.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                Collections.shuffle(factsList);
                changeFact(2);
                left.setOnClickListener(view1 -> changeFact(0));
                right.setOnClickListener(view1 -> changeFact(1));
            } else {
                noContent.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
            }
        }
        return view;
    }

    private void changeFact(int type) {
        if (type == 0) {
            if (index != 0) {
                index--;
            } else {
                index = factsList.size() - 1;
            }
        } else if (type == 1) {
            if (index != factsList.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
        fact.setText(factsList.get(index).getFact());
        Utils.customAnimation(context, fact, 500, android.R.anim.fade_in);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                String packageName = context.getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = fact.getText().toString().concat("\n");
                extraText += "See more. Download the app!\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
