package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.SpendingRVAdapter;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.service.SpendingService;

public class CategorySpendings extends AppCompatActivity {
    private Category categoryToDisplay;

    private GlobalState gs;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gs = (GlobalState) getApplication();
        setContentView(R.layout.activity_category_spendings);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_spending);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            categoryToDisplay = extras.getParcelable("category");
            if(categoryToDisplay != null){
                setTitle(categoryToDisplay.getLabel());
                SpendingRVAdapter adapter = new SpendingRVAdapter(gs, categoryToDisplay);
                rv.setAdapter(adapter);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.add :
                Intent versAddSpending = new Intent(this, AddSpending.class);
                versAddSpending.putExtra("category", categoryToDisplay);
                startActivity(versAddSpending);
                break;
            case R.id.edit :
                Intent versAddCategory = new Intent(this,AddCategory.class);
                versAddCategory.putExtra("category", categoryToDisplay);
                startActivity(versAddCategory);
                break;
        }
        return true;
    }
}
