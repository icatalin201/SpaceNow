package space.pal.sig.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.model.dto.FeedDto;
import space.pal.sig.util.GlideApp;

import static space.pal.sig.util.DateTimeUtil.DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.DISPLAY_DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.dateToString;
import static space.pal.sig.util.DateTimeUtil.stringToDate;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedDtoViewHolder> {

    public interface FeedClickListener {
        void onClick(FeedDto FeedDto);
    }

    private FeedClickListener feedClickListener;
    private List<FeedDto> feedDtos = new ArrayList<>();

    public FeedAdapter(FeedClickListener feedClickListener) {
        this.feedClickListener = feedClickListener;
    }

    public void addItems(List<FeedDto> feedDtos) {
        this.feedDtos.addAll(feedDtos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedDtoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.feed_item, viewGroup, false);
        return new FeedDtoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedDtoViewHolder feedDtoViewHolder, int i) {
        feedDtoViewHolder.render(feedDtos.get(i));
    }

    @Override
    public int getItemCount() {
        return feedDtos.size();
    }

    class FeedDtoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.date) TextView date;
        private Context context;

        FeedDtoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        void render(FeedDto feedDto) {
            String thumbnail = feedDto.getThumbnail();
            String image = feedDto.getImage();
            String url = "";
            if (thumbnail != null && !thumbnail.contains("https:")) {
                url = "https:" + thumbnail;
            } else if (image != null && !image.contains("https:")) {
                url = "https:" + image;
            }
            GlideApp.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .autoClone()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH))
                    .error(R.drawable.ic_placeholder)
                    .into(this.image);
            title.setText(feedDto.getTitle());
            Date d = stringToDate(feedDto.getPubDate().split("T")[0], DATE_FORMAT);
            date.setText(dateToString(d, DISPLAY_DATE_FORMAT));
        }

        @OnClick(R.id.feed_item)
        void onClick() {
            feedClickListener.onClick(feedDtos.get(getAdapterPosition()));
        }

    }
}
