package space.pal.sig.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.activity.ImageActivity;
import space.pal.sig.model.Apod;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private Context context;
    private ArrayList<Apod> apodList;

    public ImagesAdapter(Context context, ArrayList<Apod> apodList) {
        this.context = context;
        this.apodList = apodList;
    }

    public void addItems(List<Apod> apodList) {
        this.apodList.addAll(apodList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, viewGroup, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder imagesViewHolder, int i) {
        Apod apod = this.apodList.get(i);
        Glide.with(context)
                .load(apod.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imagesViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.apodList.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ImageActivity.class);
            Apod apod = apodList.get(getAdapterPosition());
            intent.putExtra("hdurl", apod.getHdurl());
            intent.putExtra("url", apod.getUrl());
            intent.putExtra("title", apod.getTitle());
            context.startActivity(intent);
        }
    }
}
