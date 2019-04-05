package space.pal.sig.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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

public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.GlossaryViewHolder> {

    private Context context;
    private ArrayList<Glossary> glossaryList;

    public GlossaryAdapter(Context context, ArrayList<Glossary> glossaryList) {
        this.context = context;
        this.glossaryList = glossaryList;
    }

    public void addAll(List<Glossary> glossaryList) {
        this.glossaryList.addAll(glossaryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlossaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.glossary_item, viewGroup, false);
        return new GlossaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlossaryViewHolder glossaryViewHolder, int i) {
        Glossary glossary = this.glossaryList.get(i);
        glossaryViewHolder.item.setText(glossary.getName());
    }

    @Override
    public int getItemCount() {
        return this.glossaryList.size();
    }

    class GlossaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView item;

        GlossaryViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_baseline_arrow_right_24px);
            drawable.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            item.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GlossaryActivity.class);
            intent.putExtra(GlossaryFragment.GLOSSARY_OBJECT, glossaryList.get(getAdapterPosition()));
            intent.putParcelableArrayListExtra("objects", glossaryList);
            context.startActivity(intent);
        }
    }
}
