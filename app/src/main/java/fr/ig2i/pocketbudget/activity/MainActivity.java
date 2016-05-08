package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.dao.DataBaseHelper;
import fr.ig2i.pocketbudget.dao.EarningDAO;
import fr.ig2i.pocketbudget.model.Earning;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] listArray = { "Dashboard", "Dépenses", "Revenus", "Rapport", "Paramètres"};
    RelativeLayout spending;
    RelativeLayout earning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*EarningDAO earningDAO = new  EarningDAO(getApplicationContext());

        Earning earning1 = new Earning("Salaire", new Date() , 1000.0);
        Earning earning2 = new Earning("Remboursement", new Date() , 50.0);
        earningDAO.createEarning(earning1);
        earningDAO.createEarning(earning2);
        List<Earning> earnings = earningDAO.getAllEarnings();
        for (Earning earning: earnings) {
            Log.i("onCreate", earning.toString());
        }*/

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spending = (RelativeLayout) findViewById(R.id.spending);
        spending.setOnClickListener(this);
        earning = (RelativeLayout) findViewById(R.id.earning);
        earning.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.spending :
                Intent versSpendings = new Intent(this,Spendings.class);
                startActivity(versSpendings);
                break;
            case R.id.earning:
                Intent versEarnings = new Intent(this,Earnings.class);
                startActivity(versEarnings);
                break;
        }
    }

    private void addDrawerItems() {
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // display view for selected nav drawer item
                //displayView(position);
                Log.i("tag","clicked on item "+ Integer.toString(position));
                String item = listArray[position];
                Log.i("tag", item);
                if (item != getTitle().toString()){
                    switch (item){//"Dashboard", "Dépenses", "Revenus", "Rapport", "Paramètres"
                        case "Dashboard":
                            Intent versDashboard = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(versDashboard);
                            break;
                        case "Dépenses":
                            Intent versCatSpendings = new Intent(getApplicationContext(),CategorySpendings.class);
                            startActivity(versCatSpendings);
                            break;
                        case "Revenus":
                            Intent versEarnings = new Intent(getApplicationContext(),Earnings.class);
                            startActivity(versEarnings);
                            break;
                        case "Rapport": break;
                        case "Paramètres": break;
                    }
                }

            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
