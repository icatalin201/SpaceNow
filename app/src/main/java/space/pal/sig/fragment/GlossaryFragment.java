package space.pal.sig.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.pal.sig.R;
import space.pal.sig.adapter.GlossaryAdapter;
import space.pal.sig.model.Glossary;
import space.pal.sig.network.Client;
import space.pal.sig.network.Service;
import space.pal.sig.util.Utils;

public class GlossaryFragment extends Fragment {

    private CoordinatorLayout coordinatorLayout;
    private Context context;
    private ProgressBar progressBar;
    private GlossaryAdapter glossaryAdapter;
    private RecyclerView glossaryRecycler;
    private RelativeLayout noContent;

    public static final String GLOSSARY_OBJECT = "glossary_object";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glossary, container, false);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        progressBar = view.findViewById(R.id.progress_bar);
        glossaryRecycler = view.findViewById(R.id.glossary_recycler);
        noContent = view.findViewById(R.id.no_content);
        context = getActivity();
        if (context != null) {
            glossaryAdapter = new GlossaryAdapter(context, new ArrayList<>());
            glossaryRecycler.setLayoutManager(new LinearLayoutManager(context));
            glossaryRecycler.setHasFixedSize(true);
            glossaryRecycler.setAdapter(glossaryAdapter);
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar())
                    .setTitle(getString(R.string.glossary));
            boolean connected = Utils.isNetworkConnected(context);
            if (connected) {
                makeCall();
            } else {
                progressBar.setVisibility(View.GONE);
                glossaryRecycler.setVisibility(View.GONE);
                noContent.setVisibility(View.VISIBLE);
                notifyInternet();
            }
        }
        return view;
    }

    private void makeCall() {
        Service service = Client.getRetrofitClient(Utils.HUBBLE_URL).create(Service.class);
        Call<List<Glossary>> call = service.getGlossaryAll("all");
        call.enqueue(new Callback<List<Glossary>>() {
            @Override
            public void onResponse(Call<List<Glossary>> call, Response<List<Glossary>> response) {
                if (response.isSuccessful()) {
                    List<Glossary> glossaryList = response.body();
                    glossaryAdapter.addAll(glossaryList);
                    progressBar.setVisibility(View.GONE);
                    glossaryRecycler.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    Utils.customAnimation(context, glossaryRecycler, 500, android.R.anim.fade_in);
                }
                else {
                    errorCall();
                }
            }

            @Override
            public void onFailure(Call<List<Glossary>> call, Throwable t) {
                errorCall();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void errorCall() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Something has gone wrong. :(", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

    private void notifyInternet() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No Internet Connection", 8000);
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.setAction("Retry", view -> ((AppCompatActivity) context).recreate());
        snackbar.show();
    }

}
