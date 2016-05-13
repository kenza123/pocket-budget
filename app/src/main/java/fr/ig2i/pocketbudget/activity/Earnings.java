package fr.ig2i.pocketbudget.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.EarningRVAdapter;

public class Earnings extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    private ImageView addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gs = (GlobalState) getApplication();
        setContentView(R.layout.activity_earnings);
        Context context = Earnings.this;

        addButton = (ImageView) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_earning);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        EarningRVAdapter adapter = new EarningRVAdapter(gs);
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
            Intent versAddEarning = new Intent(this,AddEarning.class);
            startActivity(versAddEarning);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_button:
                Intent versAddEarning = new Intent(this,AddEarning.class);
                startActivity(versAddEarning);
                break;
        }
    }
}
