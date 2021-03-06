package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.adapter.CategoryRVAdapter;

public class Categories extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    CategoryRVAdapter adapter;
    private ImageView addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gs = (GlobalState) getApplication();
        setContentView(R.layout.activity_spendings);

        addButton = (ImageView) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new CategoryRVAdapter(gs);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_button:
                Intent versAddCategory = new Intent(this,AddCategory.class);
                startActivity(versAddCategory);
                break;
        }
    }
}
