package fr.ig2i.pocketbudget.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.EarningRVAdapter;
import fr.ig2i.pocketbudget.adapter.RVAdapter;
import fr.ig2i.pocketbudget.service.EarningService;

public class Earnings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        EarningService earningService = new EarningService();

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_earning);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        EarningRVAdapter adapter = new EarningRVAdapter(earningService.getAllEarnings());
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
            //Intent versSpendings = new Intent(this,Spendings.class);
            //startActivity(versSpendings);
        }
        return true;
    }
}
