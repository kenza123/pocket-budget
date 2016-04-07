package fr.ig2i.pocketbudget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CategorySpendings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String categoryName = "";
        int categoryId = 0;
        if (extras != null) {
            categoryName = extras.getString("category_name");
            categoryId = Integer.parseInt(extras.getString("category_id"));
        }
        setTitle(categoryName);
        setContentView(R.layout.activity_category_spendings);

        //Here we can after extract data from dataBase and intialize data
        Spending spe = new Spending();
        spe.initializeData();

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_spending);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        SpendingRVAdapter adapter = new SpendingRVAdapter(spe.getCategorySpendings());
        rv.setAdapter(adapter);
    }
}
