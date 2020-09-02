package space.pal.sig.view.apod;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.RequiredArgsConstructor;
import space.pal.sig.R;
import space.pal.sig.model.Apod;
import space.pal.sig.model.MediaType;

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 **/
@RequiredArgsConstructor
public class ApodHorizontalAdapter extends RecyclerView.Adapter<ApodHorizontalAdapter.ViewHolder> {

    private static final int START = 1;
    private static final int MIDDLE = 2;
    private static final int END = 3;

    private final SelectApodListener listener;
    private List<Apod> apodList = new ArrayList<>();

    public void submitList(List<Apod> apodList) {
        this.apodList.clear();
        this.apodList.addAll(apodList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apod_horizontal_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return START;
        } else if (position == getItemCount() - 1) {
            return END;
        } else {
            return MIDDLE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(apodList.get(position), getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apod_card_image)
        AppCompatImageView image;
        @BindView(R.id.apod_card_video)
        WebView video;
        @BindView(R.id.apod_card)
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.apod_card_image)
        void onClickImage() {
            listener.onClick(apodList.get(getBindingAdapterPosition()));
        }

        @SuppressLint("SetJavaScriptEnabled")
        void render(Apod apod, int viewType) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                    card.getLayoutParams();
            params.setMargins(viewType == START ? 30 : 0, 0, 30, 30);
            card.setLayoutParams(params);
            if (apod.getType() == MediaType.IMAGE) {
                image.setVisibility(View.VISIBLE);
                video.setVisibility(View.GONE);
                image.setContentDescription(apod.getTitle());
                Picasso.get().load(apod.getUrl())
                        .centerCrop()
                        .fit()
                        .into(image);
            } else {
                image.setVisibility(View.GONE);
                video.setVisibility(View.VISIBLE);
                video.getSettings().setJavaScriptEnabled(true);
                video.loadUrl(apod.getUrl());
                video.setWebChromeClient(new WebChromeClient());
            }
        }
    }
}
