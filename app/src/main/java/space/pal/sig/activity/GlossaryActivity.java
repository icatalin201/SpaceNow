package space.pal.sig.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import space.pal.sig.R;
import space.pal.sig.adapter.MoreGlossary;
import space.pal.sig.fragment.GlossaryFragment;
import space.pal.sig.model.Glossary;

public class GlossaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.glossary);
        TextView name = findViewById(R.id.name);
        TextView definition = findViewById(R.id.definition);
        RecyclerView recyclerView = findViewById(R.id.glossary_recycler);
        MoreGlossary moreGlossary = new MoreGlossary(this, new ArrayList<>());
        recyclerView.setAdapter(moreGlossary);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        Glossary glossary = getIntent().getParcelableExtra(GlossaryFragment.GLOSSARY_OBJECT);
        List<Glossary> glossaryArrayList = getIntent().getParcelableArrayListExtra("objects");
        Collections.shuffle(glossaryArrayList);
        moreGlossary.addAll(glossaryArrayList);
        name.setText(glossary.getName());
        definition.setText(glossary.getDefinition());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
