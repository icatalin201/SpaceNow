package space.pal.sig.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import space.pal.sig.R;
import space.pal.sig.activity.GlossaryActivity;
import space.pal.sig.fragment.GlossaryFragment;
import space.pal.sig.model.Glossary;

public class MoreGlossary extends RecyclerView.Adapter<MoreGlossary.GlossaryViewHolder> {

    private Context context;
    private ArrayList<Glossary> glossaryArrayList;

    public MoreGlossary(Context context, ArrayList<Glossary> glossaryArrayList) {
        this.context = context;
        this.glossaryArrayList = glossaryArrayList;
    }

    public void addAll(List<Glossary> glossaryList) {
        this.glossaryArrayList.addAll(glossaryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlossaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.glossary_card, viewGroup, false);
        return new GlossaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlossaryViewHolder glossaryViewHolder, int i) {
        Glossary glossary = this.glossaryArrayList.get(i);
        glossaryViewHolder.textView.setText(glossary.getName());
    }

    @Override
    public int getItemCount() {
        return this.glossaryArrayList.size();
    }

    class GlossaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private TextView textView;

        GlossaryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            textView = itemView.findViewById(R.id.name);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GlossaryActivity.class);
            intent.putExtra(GlossaryFragment.GLOSSARY_OBJECT, glossaryArrayList.get(getAdapterPosition()));
            intent.putParcelableArrayListExtra("objects", glossaryArrayList);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish();
        }
    }
}
