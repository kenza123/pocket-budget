package fr.ig2i.pocketbudget.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;


public class Welcome extends AppCompatActivity implements View.OnClickListener {
    private GlobalState gs;
    private EditText edtBalance;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        edtBalance = (EditText) findViewById(R.id.balance_editText);
        btnCreate = (Button) findViewById(R.id.create_button);

        edtBalance.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (valideFields()) {
            //Insérer les informations dans la table solde
            Intent versMainActivity = new Intent(this,MainActivity.class);
            versMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(versMainActivity);
            finish();
        } else {
            gs.alerter("Données invalides");
        }
    }

    private boolean valideFields(){
        return notNull(edtBalance.getText().toString()) && Double.parseDouble(edtBalance.getText().toString()) > 0;
    }

    private boolean notNull(String value){
        return (value != null && !("").equals(value));
    }
}
