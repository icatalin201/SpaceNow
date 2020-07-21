package space.pal.sig.view.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import space.pal.sig.R;
import space.pal.sig.model.News;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class NewsAdapter extends PagedListAdapter<News, NewsAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<News> newsItemCallback =
            new DiffUtil.ItemCallback<News>() {
                @Override
                public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public NewsAdapter() {
        super(newsItemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.news_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_image)
        ImageView image;
        @BindView(R.id.news_title)
        TextView title;
        @BindView(R.id.news_description)
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(@Nullable News news) {
            if (news == null) return;
            String imageUrl = news.getImage();
            if (!imageUrl.contains("https:") || !imageUrl.contains("http:")) {
                imageUrl = "https:".concat(imageUrl);
            }
            Picasso.get().load(imageUrl)
                    .resize(100, 100)
                    .centerCrop().into(image);
            image.setContentDescription(news.getTitle());
            title.setText(news.getTitle());
            title.setContentDescription(news.getTitle());
            description.setText(news.getDescription());
            description.setContentDescription(news.getDescription());
        }
    }
}
