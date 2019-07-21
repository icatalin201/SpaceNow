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
import space.pal.sig.R;
import space.pal.sig.model.Glossary;

public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.GlossaryViewHolder> {

    public interface GlossaryClickListener {
        void onClick(Glossary glossary);
    }

    private GlossaryClickListener glossaryClickListener;
    private List<Glossary> glossaryList = new ArrayList<>();

    public GlossaryAdapter(GlossaryClickListener glossaryClickListener) {
        this.glossaryClickListener = glossaryClickListener;
    }

    public void addAll(List<Glossary> glossaryList) {
        this.glossaryList.clear();
        this.glossaryList.addAll(glossaryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlossaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.glossary_item, viewGroup, false);
        return new GlossaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlossaryViewHolder glossaryViewHolder, int i) {
        glossaryViewHolder.render(glossaryList.get(i));
    }

    @Override
    public int getItemCount() {
        return this.glossaryList.size();
    }

    class GlossaryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;

        GlossaryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void render(Glossary glossary) {
            name.setText(glossary.getName());
        }

        @OnClick(R.id.item)
        void onClick() {
            glossaryClickListener.onClick(glossaryList.get(getAdapterPosition()));
        }

    }
}
