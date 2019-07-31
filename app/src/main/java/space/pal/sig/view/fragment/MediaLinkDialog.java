package space.pal.sig.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.pal.sig.R;
import space.pal.sig.model.dto.LaunchDto;
import space.pal.sig.view.activity.WebViewActivity;
import space.pal.sig.view.viewmodel.MainViewModel;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static space.pal.sig.view.activity.WebViewActivity.TITLE;
import static space.pal.sig.view.activity.WebViewActivity.URL;

public class MediaLinkDialog extends DialogFragment {

    private AppCompatActivity appCompatActivity;
    private Unbinder unbinder;
    @BindView(R.id.media_layout) LinearLayout layout;
    @BindView(R.id.name) TextView name;

    public MediaLinkDialog() { }

    public static MediaLinkDialog getInstance() {
        return new MediaLinkDialog();
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
        View view = inflater.inflate(R.layout.media_link_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        MainViewModel mainViewModel = ViewModelProviders.of(appCompatActivity).get(MainViewModel.class);
        LaunchDto launchDto = mainViewModel.getSelectedLaunch();
        String title = launchDto.getName();
        String[] urls = launchDto.getVidURLs();
        for (int i = 0; i < urls.length; i++) {
            final String url = urls[i];
            Button button = new Button(appCompatActivity);
            button.setText(String.format(Locale.getDefault(), "Link %d", i + 1));
            button.setOnClickListener(view1 -> {
                Intent intent = new Intent(appCompatActivity, WebViewActivity.class);
                intent.putExtra(TITLE, title);
                intent.putExtra(URL, url);
                startActivity(intent);
                onClose();
            });
            layout.addView(button);
        }
        name.setText(title);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
    }

    @OnClick(R.id.close)
    void onClose() {
        dismiss();
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
