package space.pal.sig.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.RequiredArgsConstructor;
import space.pal.sig.R;
import space.pal.sig.model.dto.SpaceFlightNewsDto;

import static space.pal.sig.util.DateTimeUtil.formatTimestamp;

@RequiredArgsConstructor
public class SpaceNewsAdapter extends RecyclerView.Adapter<SpaceNewsAdapter.SpaceNewsViewHolder> {

    public interface SpaceNewsClickListener {
        void onClick(SpaceFlightNewsDto spaceFlightNewsDto);
    }

    private List<SpaceFlightNewsDto> newsDtoList = new ArrayList<>();
    private final SpaceNewsClickListener spaceNewsClickListener;

    public void add(List<SpaceFlightNewsDto> newsDtos) {
        newsDtoList.addAll(newsDtos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpaceNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.space_news_item, parent, false);
        return new SpaceNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceNewsViewHolder holder, int position) {
        holder.render(newsDtoList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsDtoList.size();
    }

    class SpaceNewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.categories) TextView categories;
        @BindView(R.id.tags) TextView tags;
        @BindView(R.id.date) TextView date;
        private Context context;

        SpaceNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void render(SpaceFlightNewsDto newsDto) {
            Glide.with(context)
                    .load(newsDto.getFeaturedImage())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView);
            name.setText(newsDto.getTitle());
            date.setText(formatTimestamp(newsDto.getDatePublished()));
            String tagsString = TextUtils.join(", ", newsDto.getTags());
            String categoriesString = TextUtils.join(", ", newsDto.getCategories());
            categories.setText(categoriesString);
            tags.setText(tagsString);
        }

        @OnClick(R.id.item)
        void onClick() {
            spaceNewsClickListener.onClick(newsDtoList.get(getAdapterPosition()));
        }
    }
}
