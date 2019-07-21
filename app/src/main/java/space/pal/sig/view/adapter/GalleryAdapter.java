package space.pal.sig.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.model.Apod;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ApodViewHolder> {

    private ApodAdapter.ApodClickListener apodClickListener;
    private List<Apod> apodList = new ArrayList<>();

    public GalleryAdapter(ApodAdapter.ApodClickListener apodClickListener) {
        this.apodClickListener = apodClickListener;
    }

    public void add(List<Apod> apods) {
        this.apodList.clear();
        Collections.shuffle(apods);
        this.apodList.addAll(apods);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false);
        return new ApodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodViewHolder holder, int position) {
        holder.render(apodList.get(position));
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    class ApodViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView apodImage;
        private Context context;

        ApodViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void render(Apod apod) {
            Glide.with(context)
                    .load(apod.getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .autoClone()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH))
                    .into(apodImage);
        }

        @OnClick(R.id.image)
        void onClick() {
            apodClickListener.onClick(apodList.get(getAdapterPosition()));
        }
    }

}
