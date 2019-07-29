package space.pal.sig.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.RequiredArgsConstructor;
import space.pal.sig.R;
import space.pal.sig.model.dto.AgencyDto;
import space.pal.sig.model.dto.MissionDto;

@RequiredArgsConstructor
public class MissionsAdapter extends RecyclerView.Adapter<MissionsAdapter.MissionsViewHolder> {

    public interface MissionClickListener {
        void onMissionClick(MissionDto missionDto);
    }

    private final MissionClickListener missionClickListener;
    private List<MissionDto> missionDtos = new ArrayList<>();

    public void add(List<MissionDto> launchMissions) {
        this.missionDtos.addAll(launchMissions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MissionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.mission_item, viewGroup, false);
        return new MissionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionsViewHolder missionsViewHolder, int i) {
        missionsViewHolder.render(missionDtos.get(i));
    }

    @Override
    public int getItemCount() {
        return missionDtos.size();
    }

    class MissionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.payloads) TextView payloads;
        @BindView(R.id.agencies) TextView agencies;

        MissionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item)
        void onClick() {
            missionClickListener.onMissionClick(missionDtos.get(getAdapterPosition()));
        }

        void render(MissionDto missionDto) {
            List<AgencyDto> agencies = missionDto.getAgencies();
            if (agencies != null && agencies.size() > 0) {
                this.agencies.setText(String.format("#%s",
                        agencies.get(0).getName().replace(" ", "")));
                this.agencies.setVisibility(View.VISIBLE);
            } else {
                this.agencies.setVisibility(View.GONE);
            }
            name.setText(missionDto.getName());
            description.setText(missionDto.getDescription());
            payloads.setText(String.format("Payloads: %s", missionDto.getPayloads().size()));
        }
    }
}
