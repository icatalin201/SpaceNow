package space.pal.sig.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.activity.NewsActivity;
import space.pal.sig.fragment.NewsFragment;
import space.pal.sig.model.NewsRelease;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private ArrayList<NewsRelease> newsPreviewList;

    public NewsAdapter(Context context, ArrayList<NewsRelease> newsPreviewList) {
        this.context = context;
        this.newsPreviewList = newsPreviewList;
    }

    public void addItems(List<NewsRelease> newsPreviews) {
        this.newsPreviewList.addAll(newsPreviews);
        notifyDataSetChanged();
    }

    public ArrayList<NewsRelease> getNewsPreviewList() {
        return this.newsPreviewList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_preview_card, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        NewsRelease newsPreview = this.newsPreviewList.get(i);
        newsViewHolder.title.setText(newsPreview.getName());
        Glide.with(context)
                .load(newsPreview.getThumbnail())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newsViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.newsPreviewList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private LinearLayout cardView;
        private ImageView imageView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            cardView = itemView.findViewById(R.id.news_item);
            imageView = itemView.findViewById(R.id.image);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra(NewsFragment.NEWSP_OBJECT, newsPreviewList.get(getAdapterPosition()));
            intent.putParcelableArrayListExtra("objects", newsPreviewList);
            context.startActivity(intent);
        }
    }
}
