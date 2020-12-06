package space.pal.sig.old.view.apod;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import space.pal.sig.R;
import space.pal.sig.old.Space;
import space.pal.sig.old.model.Apod;
import space.pal.sig.old.view.SpaceBaseActivity;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class ImageActivity extends SpaceBaseActivity {

    public static final String IMAGE_ID = "image_id";

    @BindView(R.id.apod_image)
    AppCompatImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    ImageViewModelFactory factory;
    private ImageViewModel imageViewModel;
    private Apod apod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        setSupportActionBar(toolbar);
        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Space.getApplicationComponent().inject(this);
        String imageId = getIntent().getStringExtra(IMAGE_ID);
        imageViewModel = new ViewModelProvider(this, factory).get(ImageViewModel.class);
        imageViewModel.getImage().observe(this, this::consumeImage);
        imageViewModel.loadImage(imageId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.description:
                if (apod != null) {
                    Dialog dialog = new AlertDialog
                            .Builder(this, R.style.AppTheme_Dialog)
                            .setTitle(apod.getTitle())
                            .setMessage(HtmlCompat.fromHtml(apod.getExplanation(), 0))
                            .setPositiveButton(R.string.ok, (dialogInterface, i) ->
                                    dialogInterface.dismiss())
                            .create();
                    dialog.show();
                }
                break;
            case R.id.download:
                if (apod != null) {
                    boolean writePermission = ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writePermission) {
                        imageViewModel.downloadImage(apod.getHdUrl());
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imageViewModel.downloadImage(apod.getHdUrl());
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void consumeImage(Apod apod) {
        this.apod = apod;
        image.setContentDescription(apod.getTitle());
        Picasso.get().load(apod.getUrl()).centerCrop().fit().into(image);
    }
}