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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.RequiredArgsConstructor;
import space.pal.sig.R;
import space.pal.sig.model.dto.LaunchDto;
import space.pal.sig.model.dto.LocationDto;
import space.pal.sig.model.dto.RocketDto;

@RequiredArgsConstructor
public class RocketLaunchesAdapter extends RecyclerView.Adapter<RocketLaunchesAdapter.RocketLaunchesViewHolder> {

    public interface RocketLaunchClickListener {
        void onRocketLaunchClick(LaunchDto launchDto);
    }

    private final RocketLaunchClickListener rocketLaunchClickListener;
    private List<LaunchDto> launchList = new ArrayList<>();

    public void add(List<LaunchDto> launches) {
        this.launchList.addAll(launches);
        notifyDataSetChanged();
    }

    public void remove() {
        this.launchList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RocketLaunchesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.launch_item, viewGroup, false);
        return new RocketLaunchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RocketLaunchesViewHolder rocketLaunchesViewHolder, int i) {
       rocketLaunchesViewHolder.render(launchList.get(i));
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    class RocketLaunchesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.agency) TextView agency;
        @BindView(R.id.location) TextView location;
        @BindView(R.id.windowstart) TextView windowStart;
        private Context context;

        RocketLaunchesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void render(LaunchDto launch) {
            RocketDto launchRocket = launch.getRocket();
            LocationDto launchLocation = launch.getLocation();
            String locationString = "";
            if (launchLocation.getPads() != null && launchLocation.getPads().size() > 0) {
                locationString = launchLocation.getPads().get(0).getName();
            }
            Glide.with(context)
                    .load(launchRocket.getImageURL())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView);
            name.setText(launch.getName());
            agency.setText(launch.getLsp().getName());
            location.setText(locationString);
            windowStart.setText(launch.getWindowstart());
        }

        @OnClick(R.id.item)
        void onClick() {
            rocketLaunchClickListener.onRocketLaunchClick(launchList.get(getAdapterPosition()));
        }
    }
}
