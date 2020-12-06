package space.pal.sig.old.view.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.old.model.News;

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

    private final SelectNewsListener listener;

    public NewsAdapter(SelectNewsListener listener) {
        super(newsItemCallback);
        this.listener = listener;
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

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_image)
        AppCompatImageView image;
        @BindView(R.id.news_title)
        AppCompatTextView title;
        @BindView(R.id.news_description)
        AppCompatTextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(@Nullable News news) {
            if (news == null) return;
            String imageUrl = news.getImage();
            if (!imageUrl.contains("https:") || !imageUrl.contains("http:")) {
                imageUrl = "https:".concat(imageUrl);
            }
            Picasso.get().load(imageUrl)
                    .centerCrop()
                    .fit()
                    .into(image);
            image.setContentDescription(news.getTitle());
            title.setText(news.getTitle());
            title.setContentDescription(news.getTitle());
            description.setText(news.getDescription());
            description.setContentDescription(news.getDescription());
        }

        @OnClick(R.id.news_card_view)
        void onClickNews() {
            listener.onClick(getItem(getBindingAdapterPosition()));
        }
    }
}
