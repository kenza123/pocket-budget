package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Category;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] listArray = {"Dashboard", "Dépenses", "Revenus", "Rapport", "Paramètres"};
    private RelativeLayout spending;
    private RelativeLayout earning;
    private ImageView addButton;
    private PieChart pieChart;
    private RelativeLayout chartLayout;
    private Boolean availableData;
    private GlobalState gs;
    private String TAG;
    private ArrayList<Float> yData = new ArrayList<Float>();
    private ArrayList<String> xData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gs = (GlobalState) getApplication();
        availableData = initiateChartData();

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spending = (RelativeLayout) findViewById(R.id.spending);
        spending.setOnClickListener(this);
        earning = (RelativeLayout) findViewById(R.id.earning);
        earning.setOnClickListener(this);
        addButton = (ImageView) findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        chartLayout = (RelativeLayout) findViewById(R.id.chart_layout);
        pieChart = (PieChart) findViewById(R.id.pie_chart);
        //pieChart = new PieChart(this);



        // add pie chart to main layout
        //chartLayout.addView(pieChart);

        // configure pie chart
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        //pieChart.setMinimumWidth(chartLayout.getWidth());

        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(0);
        pieChart.setHoleRadius(20);
        pieChart.setTransparentCircleRadius(25);

        // enable rotation of the chart by touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        // set a chart value selected listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                //gs.alerter(xData[e.getXIndex()] + " = " + e.getVal() + "%");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        if (availableData) {
            addDataToChart();
        }

        // customize legends
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        TextView balanceAmount =  (TextView) findViewById(R.id.balance_amount);
        balanceAmount.setText(Double.toString(gs.getBalanceService().getBalanceAmount()) + "€");

        TextView earningAmount = (TextView) findViewById(R.id.earning_amount);
        earningAmount.setText(Double.toString(gs.getEarningService().countSumEarningsOfTheMonth()) + "€");

        TextView budgetAmount = (TextView) findViewById(R.id.budget_amount);
        budgetAmount.setText(Double.toString(gs.getCategoryService().countTheTotalBudget()) + "€");

        TextView spendingAmout = (TextView) findViewById(R.id.spending_amount);
        spendingAmout.setText(Double.toString(gs.getSpendingService().countTotalSpendingsOfTheMonth()) + "€");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = gs.getPrefs();
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            Intent versWelcome = new Intent(this, Welcome.class);
            startActivity(versWelcome);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        availableData = initiateChartData();
        if (availableData) {
            addDataToChart();
        }

        TextView balanceAmount =  (TextView) findViewById(R.id.balance_amount);
        balanceAmount.setText(Double.toString(gs.getBalanceService().getBalanceAmount()) + "€");

        TextView earningAmount = (TextView) findViewById(R.id.earning_amount);
        earningAmount.setText(Double.toString(gs.getEarningService().countSumEarningsOfTheMonth()) + "€");

        TextView budgetAmount = (TextView) findViewById(R.id.budget_amount);
        budgetAmount.setText(Double.toString(gs.getCategoryService().countTheTotalBudget()) + "€");

        TextView spendingAmout = (TextView) findViewById(R.id.spending_amount);
        spendingAmout.setText(Double.toString(gs.getSpendingService().countTotalSpendingsOfTheMonth()) + "€");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spending:
                Intent versSpendings = new Intent(this, Categories.class);
                startActivity(versSpendings);
                break;
            case R.id.earning:
                Intent versEarnings = new Intent(this, Earnings.class);
                startActivity(versEarnings);
                break;
            case R.id.add_button:
                Intent versAddItem = new Intent(this, AddItem.class);
                startActivity(versAddItem);
                break;
        }
    }

    private void addDrawerItems() {
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // display view for selected nav drawer item
                //displayView(position);
                String item = listArray[position];
                if (item != getTitle().toString()) {
                    switch (item) {//"Dashboard", "Dépenses", "Revenus", "Rapport", "Paramètres"
                        case "Dashboard":
                            Intent versDashboard = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(versDashboard);
                            break;
                        case "Dépenses":
                            Intent versCatSpendings = new Intent(getApplicationContext(), CategorySpendings.class);
                            startActivity(versCatSpendings);
                            break;
                        case "Revenus":
                            Intent versEarnings = new Intent(getApplicationContext(), Earnings.class);
                            startActivity(versEarnings);
                            break;
                        case "Rapport":
                            break;
                        case "Paramètres":
                            break;
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
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private boolean initiateChartData(){
        List<Category> categories = gs.getCategoryService().getAllNotDeletedCategories();
        Double totalSpendings = gs.getSpendingService().countTotalSpendingsOfTheMonth();
        xData.clear();
        yData.clear();

        if (categories.size() > 0) {
            for(Category cat : categories){
                xData.add(cat.getLabel());
                Double categorySpendings = gs.getSpendingService().getTotalSpendingsByCategoryID(cat.getId());
                Double prog = (categorySpendings * 100) / totalSpendings;
                yData.add(Float.parseFloat(Double.toString(prog)));
            }
            return true;
        } else {
            return false;
        }

    }

    private void addDataToChart(){
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.size(); i++)
            yVals1.add(new Entry(yData.get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.size(); i++)
            xVals.add(xData.get(i));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : gs.getJoyfulTemplate())
            colors.add(c);

        for (int c : gs.getColorfulTemplate())
            colors.add(c);

        for (int c : gs.getNiceTemplate())
            colors.add(c);

        for (int c : gs.getDarkTemplate())
            colors.add(c);

        for (int c : gs.getPaleTemplate())
            colors.add(c);

        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }
}
