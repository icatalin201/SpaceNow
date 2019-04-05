package space.pal.sig.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.adapter.ImagesAdapter;
import space.pal.sig.model.Apod;
import space.pal.sig.util.SqlService;
import space.pal.sig.util.Utils;

public class ImagesFragment extends Fragment {

    private CoordinatorLayout coordinatorLayout;
    private Context context;
    private ProgressBar progressBar;
    private RelativeLayout noContent;
    private ImagesAdapter imagesAdapter;
    private RelativeLayout content;
    private RelativeLayout loadingBar;

    private int presentPage = 0;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private List<Apod> apodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        coordinatorLayout = view.findViewById(R.id.coordinator);
        progressBar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        noContent = view.findViewById(R.id.no_content);
        content = view.findViewById(R.id.content);
        loadingBar = view.findViewById(R.id.loading);
        context = getActivity();
        if (context != null) {
            apodList = SqlService.getApodList(context, null);
            Collections.shuffle(apodList);
            recyclerView.setHasFixedSize(true);
            imagesAdapter = new ImagesAdapter(context, new ArrayList<>());
            final GridLayoutManager lm = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(imagesAdapter);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                    int visibleItemCount = lm.getChildCount();
                    int totalItemCount = lm.getItemCount();
                    int firstVisibleItem = lm.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        makeCall();
                        loading = true;
                    }
                }
            });
            Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar())
                    .setTitle(getString(R.string.images));
            boolean connected = Utils.isNetworkConnected(context);
            if (connected) {
                makeCall();
            } else {
                notifyInternet();
                errorLayout();
            }
        }
        return view;
    }

    private void errorLayout() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        noContent.setVisibility(View.VISIBLE);
        Utils.customAnimation(context, noContent, 500, android.R.anim.fade_in);
    }

    private void okLayout() {
        progressBar.setVisibility(View.GONE);
        noContent.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        Utils.customAnimation(context, content, 500, android.R.anim.fade_in);
    }

    private void makeCall() {
        if (presentPage > 0) {
            loadingBar.setVisibility(View.VISIBLE);
        }
        new Handler().postDelayed(() -> new UpdateRecycler().execute(), 1000);
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateRecycler extends AsyncTask<Void, Void, List<Apod>> {

        @Override
        protected List<Apod> doInBackground(Void... voids) {
//            String limit = (20 * presentPage) + ",20";
//            return SqlService.getApodList(context, null);
            if (Utils.isNetworkConnected(context)) {
                return apodList.subList(20 * presentPage, 20 + (20 * presentPage));
            } else return null;
        }

        @Override
        protected void onPostExecute(List<Apod> apodList) {
            super.onPostExecute(apodList);
            if (apodList != null) {
                Collections.shuffle(apodList);
                imagesAdapter.addItems(apodList);
                if (presentPage == 0) {
                    okLayout();
                } else {
                    loadingBar.setVisibility(View.GONE);
                }
                presentPage++;
            } else {
                notifyInternet();
                loadingBar.setVisibility(View.GONE);
            }
        }
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
