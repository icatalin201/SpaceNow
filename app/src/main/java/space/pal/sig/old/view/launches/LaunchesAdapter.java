package space.pal.sig.old.view.launches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.pal.sig.R;
import space.pal.sig.old.model.Launch;
import space.pal.sig.old.repository.dto.LocationDto;
import space.pal.sig.old.repository.dto.RocketDto;
import space.pal.sig.old.repository.dto.SpaceProviderDto;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public class LaunchesAdapter extends PagedListAdapter<Launch, LaunchesAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Launch> launchItemCallback =
            new DiffUtil.ItemCallback<Launch>() {
                @Override
                public boolean areItemsTheSame(@NonNull Launch oldItem, @NonNull Launch newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Launch oldItem, @NonNull Launch newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private static final int TOP = 1;
    private static final int MIDDLE = 2;
    private static final int BOTTOM = 3;
    private static final int TOP_BOTTOM = 4;
    private final SelectLaunchListener listener;

    public LaunchesAdapter(SelectLaunchListener listener) {
        super(launchItemCallback);
        this.listener = listener;
    }

    public Launch getLaunch(int position) {
        return getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && position == getItemCount() - 1) {
            return TOP_BOTTOM;
        } else if (position == 0) {
            return TOP;
        } else if (position == getItemCount() - 1) {
            return BOTTOM;
        } else {
            return MIDDLE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.launch_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(getItem(position), getItemViewType(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.launch_image)
        AppCompatImageView image;
        @BindView(R.id.launch_name)
        AppCompatTextView name;
        @BindView(R.id.launch_agency)
        AppCompatTextView agency;
        @BindView(R.id.launch_location)
        AppCompatTextView location;
        @BindView(R.id.launch_date)
        AppCompatTextView date;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.launch_layout)
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void render(Launch launch, int viewType) {
            if (launch == null) return;
            RocketDto rocketDto = launch.getRocket();
            if (rocketDto == null) return;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                    layout.getLayoutParams();
            switch (viewType) {
                case TOP_BOTTOM:
                    line.setBackgroundResource(R.drawable.launches_line_top_bottom);
                    params.topMargin = 33;
                    break;
                case TOP:
                    line.setBackgroundResource(R.drawable.launches_line_top);
                    params.topMargin = 33;
                    break;
                case BOTTOM:
                    line.setBackgroundResource(R.drawable.launches_line_bottom);
                    params.bottomMargin = 33;
                    break;
                default:
                    line.setBackgroundResource(R.drawable.launches_line_middle);
                    break;
            }
            layout.setLayoutParams(params);
            Picasso.get().load(rocketDto.getImageURL())
                    .centerCrop()
                    .fit()
                    .into(image);
            image.setContentDescription(launch.getName());
            name.setText(launch.getName());
            SpaceProviderDto spaceProvider = launch.getLaunchSpaceProvider();
            agency.setText(spaceProvider != null ? spaceProvider.getName() : "");
            LocationDto locationDto = launch.getLocation();
            if (locationDto != null &&
                    locationDto.getPads() != null &&
                    locationDto.getPads().size() > 0) {
                location.setText(locationDto.getPads().get(0).getName());
            } else {
                location.setText("");
            }
            date.setText(launch.getDate());
        }

        @OnClick(R.id.launch_layout)
        void onClick() {
            listener.onClick(getLaunch(getLayoutPosition()));
        }
    }
}
