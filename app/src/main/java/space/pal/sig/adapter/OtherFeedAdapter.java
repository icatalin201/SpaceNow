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
import space.pal.sig.activity.FeedActivity;
import space.pal.sig.model.Feed;

import static space.pal.sig.fragment.FeedFragment.NEWSP_OBJECT;

public class OtherFeedAdapter extends RecyclerView.Adapter<OtherFeedAdapter.OtherFeedViewHolder> {

    private Context context;
    private ArrayList<Feed> feedArrayList;

    public OtherFeedAdapter(Context context, ArrayList<Feed> feedArrayList) {
        this.context = context;
        this.feedArrayList = feedArrayList;
    }

    public void addItems(List<Feed> feedList) {
        this.feedArrayList.addAll(feedList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_feed_card, viewGroup, false);
        return new OtherFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherFeedViewHolder otherFeedViewHolder, int i) {
        Feed feed = this.feedArrayList.get(i);
        otherFeedViewHolder.title.setText(feed.getTitle());
        String url = feed.getThumbnail() == null ? feed.getImage() : feed.getThumbnail();
        Glide.with(context).load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(otherFeedViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return feedArrayList.size();
    }

    class OtherFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView feedItem;
        private ImageView image;
        private TextView title;

        OtherFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            feedItem = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            feedItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, FeedActivity.class);
            Feed feed = feedArrayList.get(getAdapterPosition());
            intent.putExtra(NEWSP_OBJECT, feed);
            intent.putParcelableArrayListExtra("objects", feedArrayList);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();
        }
    }
}
