package space.pal.sig.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.RequiredArgsConstructor;
import space.pal.sig.R;
import space.pal.sig.model.Rocket;

@RequiredArgsConstructor
public class RocketsAdapter extends RecyclerView.Adapter<RocketsAdapter.RocketViewHolder> {

    public interface RocketClickListener {
        void onRocketClick(Rocket rocket);
    }

    private final RocketClickListener rocketClickListener;
    private List<Rocket> rockets = new ArrayList<>();

    public void add(List<Rocket> rockets) {
        Collections.shuffle(rockets);
        this.rockets.addAll(rockets);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RocketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rocket_item, parent, false);
        return new RocketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RocketViewHolder holder, int position) {
        holder.render(rockets.get(position));
    }

    @Override
    public int getItemCount() {
        return rockets.size();
    }

    class RocketViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.image) ImageView image;
        private Context context;

        RocketViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void render(Rocket rocket) {
            name.setText(rocket.getName());
            RequestManager manager = Glide.with(context);
            manager.load(rocket.getImageURL())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .autoClone()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH))
                    .error(manager.load(R.drawable.ic_placeholder))
                    .into(image);
        }

        @OnClick(R.id.item)
        void onClick() {
            rocketClickListener.onRocketClick(rockets.get(getAdapterPosition()));
        }
    }
}
