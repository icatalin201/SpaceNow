package space.pal.sig.view.apod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.model.Apod;

/**
 * SpaceNow
 * Created by Catalin on 7/28/2020
 **/
public class ApodVerticalAdapter extends PagedListAdapter<Apod, ApodVerticalAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Apod> apodItemCallback =
            new DiffUtil.ItemCallback<Apod>() {
                @Override
                public boolean areItemsTheSame(@NonNull Apod oldItem, @NonNull Apod newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Apod oldItem, @NonNull Apod newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private final SelectApodListener listener;

    protected ApodVerticalAdapter(SelectApodListener listener) {
        super(apodItemCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.apod_vertical_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apod_card_image)
        AppCompatImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.apod_card_image)
        void onClickImage() {
            listener.onClick(getItem(getBindingAdapterPosition()));
        }

        void render(Apod apod) {
            if (apod == null) return;
            image.setContentDescription(apod.getTitle());
            Picasso.get().load(apod.getUrl())
                    .centerCrop()
                    .fit()
                    .into(image);
        }
    }
}
