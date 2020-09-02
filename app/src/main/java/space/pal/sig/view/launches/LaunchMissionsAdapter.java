package space.pal.sig.view.launches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import space.pal.sig.R;
import space.pal.sig.repository.dto.MissionDto;

/**
 * SpaceNow
 * Created by catalin.matache on 7/27/2020
 */
public class LaunchMissionsAdapter extends RecyclerView.Adapter<LaunchMissionsAdapter.ViewHolder> {

    private final List<MissionDto> missionDtoList = new ArrayList<>();

    public void submitList(List<MissionDto> missionDtoList) {
        this.missionDtoList.clear();
        this.missionDtoList.addAll(missionDtoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mission_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.render(missionDtoList.get(position));
    }

    @Override
    public int getItemCount() {
        return missionDtoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mission_name)
        AppCompatTextView name;
        @BindView(R.id.mission_description)
        AppCompatTextView description;
        @BindView(R.id.mission_payloads)
        AppCompatTextView payloads;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(MissionDto missionDto) {
            name.setText(missionDto.getName());
            description.setText(missionDto.getDescription());
            payloads.setText(String.format("Payloads: %s", missionDto.getPayloads().size()));
        }
    }
}
