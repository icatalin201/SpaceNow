package space.pal.sig.view.apod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import space.pal.sig.R;
import space.pal.sig.model.Apod;

import static space.pal.sig.util.DateUtil.formatDate;

/**
 * SpaceNow
 * Created by Catalin on 7/25/2020
 **/
public class ApodHorizontalAdapter extends RecyclerView.Adapter<ApodHorizontalAdapter.ViewHolder> {

    private List<Apod> apodList = new ArrayList<>();

    public void submitList(List<Apod> apodList) {
        apodList.clear();
        this.apodList.addAll(apodList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.apod_horizontal_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(apodList.get(position));
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apod_card_image)
        AppCompatImageView image;
        @BindView(R.id.apod_card_date)
        AppCompatTextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(Apod apod) {
            date.setText(formatDate(apod.getDate(), "dd-MM-yyyy"));
            image.setContentDescription(apod.getTitle());
            Picasso.get().load(apod.getUrl())
                    .centerCrop()
                    .fit()
                    .into(image);
        }
    }
}
