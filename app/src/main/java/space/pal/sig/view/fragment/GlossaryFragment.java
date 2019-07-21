package space.pal.sig.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.Glossary;
import space.pal.sig.view.adapter.GlossaryAdapter;
import space.pal.sig.view.viewmodel.MainViewModel;

public class GlossaryFragment extends Fragment implements GlossaryAdapter.GlossaryClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private MainViewModel mainViewModel;
    private GlossaryAdapter glossaryAdapter;
    @BindView(R.id.glossary_recycler) RecyclerView glossary;

    public GlossaryFragment() { }

    public static GlossaryFragment getInstance() {
        return new GlossaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_glossary, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        glossaryAdapter = new GlossaryAdapter(this);
        glossary.setAdapter(glossaryAdapter);
        glossary.setLayoutManager(new GridLayoutManager(appCompatActivity, 2));
        glossary.setItemAnimator(new DefaultItemAnimator());
        mainViewModel.getGlossary().observe(this, glossaries -> {
            if (glossaries != null) {
                glossaryAdapter.addAll(glossaries);
            }
        });
        return view;
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

    @Override
    public void onClick(Glossary glossary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setTitle(glossary.getName());
        builder.setMessage(glossary.getDefinition());
        builder.setPositiveButton(R.string.close, (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
