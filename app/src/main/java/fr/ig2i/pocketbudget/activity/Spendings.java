package fr.ig2i.pocketbudget.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.RVAdapter;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.service.CategoryService;

public class Spendings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendings);

        Context context = this.getApplicationContext();
        CategoryService categoryService = new CategoryService();
        List<Category> categories = new ArrayList<Category>();

        categories = categoryService.getAllCategories();

        /*Bundle extras = getIntent().getExtras();
        if (extras != null){
            String new_categ_label = extras.getString("new_categ_label");
            Double new_categ_budget = extras.getDouble("new_categ_budget");
            Double new_categ_treshold = extras.getDouble("new_categ_treshold");
            categories.add(new Category(new_categ_label,new_categ_budget,new_categ_treshold,0));
        }*/

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(categories, context);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent versAddCategory = new Intent(this,AddCategory.class);
            startActivity(versAddCategory);
        }
        return true;
    }
}
