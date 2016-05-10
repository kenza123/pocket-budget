package fr.ig2i.pocketbudget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.SpendingRVAdapter;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.service.SpendingService;

public class CategorySpendings extends AppCompatActivity {
    private Category categoryToDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_spendings);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            categoryToDisplay = extras.getParcelable("category");
            if(categoryToDisplay != null){
                //requete pour récupérer la liste des spendings de cette categorie
                setTitle(categoryToDisplay.getLabel());
                //SpendingRVAdapter adapter = new SpendingRVAdapter(spendingService.getAllSpendingsOfCategory(CategoryToDisplay));
                //...
            }
        }else{
            //don't no!
        }

        //Here we can after extract data from dataBase and intialize data
        SpendingService spendingService = new SpendingService();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_spending);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        SpendingRVAdapter adapter = new SpendingRVAdapter(spendingService.getAllSpendings());
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.add : break;
            case R.id.edit :
                //Intent versPref = new Intent(this,PrefActivity.class);
                //startActivity(versPref);
                break;
            case R.id.delete : break;
        }
        return true;
    }
}
