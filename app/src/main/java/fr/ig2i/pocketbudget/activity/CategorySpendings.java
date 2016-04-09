package fr.ig2i.pocketbudget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.SpendingRVAdapter;
import fr.ig2i.pocketbudget.model.Spending;

public class CategorySpendings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_spendings);

        Bundle extras = getIntent().getExtras();
        String categoryName = extras.getString("category_name");
        //String categoryId = extras.getString("category_id");
        //Log.i("tag",categoryId);
        //int id = Integer.parseInt(categoryId);

        setTitle(categoryName);

        //Here we can after extract data from dataBase and intialize data
        Spending spe = new Spending();
        spe.initializeData();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_spending);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        SpendingRVAdapter adapter = new SpendingRVAdapter(spe.getCategorySpendings());
        rv.setAdapter(adapter);
    }
}
