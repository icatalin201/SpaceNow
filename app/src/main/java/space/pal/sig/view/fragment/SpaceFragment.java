package space.pal.sig.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.Fact;
import space.pal.sig.view.viewmodel.MainViewModel;

public class SpaceFragment extends Fragment {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private List<Fact> factList = new ArrayList<>();
    private int position = 0;
    @BindView(R.id.fact) TextView fact;

    public SpaceFragment() { }

    public static SpaceFragment getInstance() {
        return new SpaceFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        unbinder = ButterKnife.bind(this, view);
        MainViewModel mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        mainViewModel.getFacts().observe(this, facts -> {
            if (facts != null) {
                Collections.shuffle(facts);
                factList.addAll(facts);
                displayFact();
            }
        });
        return view;
    }

    private void displayFact() {
        fact.setText(factList.get(position).getName());
    }

    @OnClick(R.id.left)
    void onLeft() {
        if (position == 0) {
            position = factList.size() - 1;
        } else {
            position--;
        }
        displayFact();
    }

    @OnClick(R.id.right)
    void onRight() {
        if (position == factList.size() - 1) {
            position = 0;
        } else {
            position++;
        }
        displayFact();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
