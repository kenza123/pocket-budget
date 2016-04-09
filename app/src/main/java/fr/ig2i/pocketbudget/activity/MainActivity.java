package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Date;
import java.util.List;

import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.dao.DataBaseHelper;
import fr.ig2i.pocketbudget.dao.EarningDAO;
import fr.ig2i.pocketbudget.model.Earning;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
                break;
        }
    }
}
