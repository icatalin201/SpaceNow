package space.pal.sig.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.activity.NewsActivity;
import space.pal.sig.fragment.NewsFragment;
import space.pal.sig.model.NewsRelease;

public class OtherNewsAdapter extends RecyclerView.Adapter<OtherNewsAdapter.OtherNewsViewHolder> {

    private Context context;
    private ArrayList<NewsRelease> newsPreviewList;

    public OtherNewsAdapter(Context context, ArrayList<NewsRelease> newsPreviewList) {
        this.context = context;
        this.newsPreviewList = newsPreviewList;
    }

    public void addAll(List<NewsRelease> newsPreviews) {
        this.newsPreviewList.addAll(newsPreviews);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherNewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_news_view, viewGroup, false);
        return new OtherNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherNewsViewHolder otherNewsViewHolder, int i) {
        NewsRelease newsPreview = this.newsPreviewList.get(i);
        otherNewsViewHolder.textView.setText(newsPreview.getName());
        Glide.with(context)
                .load(newsPreview.getThumbnail())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(otherNewsViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.newsPreviewList.size();
    }

    class OtherNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private TextView textView;
        private ImageView imageView;

        OtherNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            textView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra(NewsFragment.NEWSP_OBJECT, newsPreviewList.get(getAdapterPosition()));
            intent.putParcelableArrayListExtra("objects", newsPreviewList);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();

        }
    }
}
