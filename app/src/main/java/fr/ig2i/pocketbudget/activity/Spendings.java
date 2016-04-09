package fr.ig2i.pocketbudget.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.RVAdapter;
import fr.ig2i.pocketbudget.service.CategoryService;

public class Spendings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this.getApplicationContext();

        setContentView(R.layout.activity_spendings);

        CategoryService categoryService = new CategoryService();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(categoryService.getAllCategories(), context);
        rv.setAdapter(adapter);
    }
}
