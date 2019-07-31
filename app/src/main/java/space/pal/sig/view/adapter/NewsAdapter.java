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
import space.pal.sig.model.dto.NewsDto;
import space.pal.sig.util.GlideApp;

import static space.pal.sig.util.DateTimeUtil.DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.DISPLAY_DATE_FORMAT;
import static space.pal.sig.util.DateTimeUtil.dateToString;
import static space.pal.sig.util.DateTimeUtil.stringToDate;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public interface NewsClickListener {
        void onClick(NewsDto newsDto);
    }

    private NewsClickListener newsClickListener;
    private List<NewsDto> newsDtos = new ArrayList<>();

    public NewsAdapter(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    public void addItems(List<NewsDto> newsDtos) {
        this.newsDtos.addAll(newsDtos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.feed_item, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.render(newsDtos.get(i));
    }

    @Override
    public int getItemCount() {
        return newsDtos.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.mission) TextView mission;
        private Context context;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
        }

        void render(NewsDto newsDto) {
            String thumbnail = newsDto.getThumbnail();
            String url = "";
            if (thumbnail != null && !thumbnail.contains("https:")) {
                url = "https:" + thumbnail;
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
            title.setText(newsDto.getName());
            Date d = stringToDate(newsDto.getPublication().split("T")[0], DATE_FORMAT);
            date.setText(dateToString(d, DISPLAY_DATE_FORMAT));
            mission.setText(String.format("#%s", newsDto.getMission()));
        }

        @OnClick(R.id.feed_item)
        void onClick() {
            newsClickListener.onClick(newsDtos.get(getAdapterPosition()));
        }

    }

}
