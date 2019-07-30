package space.pal.sig.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.dto.SpaceFlightNewsDto;
import space.pal.sig.view.activity.WebViewActivity;
import space.pal.sig.view.adapter.SpaceNewsAdapter;

import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;

public class SpaceNewsFragment extends Fragment implements SpaceNewsAdapter.SpaceNewsClickListener {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    private SpaceNewsAdapter spaceNewsAdapter;
    @BindView(R.id.space_news_recycler) RecyclerView spaceNewsRecycler;

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
        View view = inflater.inflate(R.layout.fragment_space_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        spaceNewsAdapter = new SpaceNewsAdapter(this);
        spaceNewsRecycler.setHasFixedSize(true);
        spaceNewsRecycler.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(appCompatActivity);
        spaceNewsRecycler.setLayoutManager(manager);
        spaceNewsRecycler.setAdapter(spaceNewsAdapter);
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
    public void onClick(SpaceFlightNewsDto spaceFlightNewsDto) {
        Intent intent = new Intent(appCompatActivity, WebViewActivity.class);
        intent.putExtra(TITLE, spaceFlightNewsDto.getTitle());
        intent.putExtra(URL, spaceFlightNewsDto.getUrl());
        startActivity(intent);
    }
}
