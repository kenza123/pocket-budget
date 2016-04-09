package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import fr.ig2i.pocketbudget.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout spending;
    RelativeLayout earning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
