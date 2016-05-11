package fr.ig2i.pocketbudget.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Earning;

public class AddEarning extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    private EditText edtLabel;
    private EditText edtAmount;
    private EditText edtDate;
    private Button btnCreate;

    private Earning earningToUpdate;
    private String TAG = "AddEarning";

    private DatePickerDialog datePickerDialog;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_earning);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            earningToUpdate = extras.getParcelable("earning");
        }

        gs = (GlobalState) getApplication();

        edtLabel = (EditText) findViewById(R.id.earning_label_editText);
        edtAmount = (EditText) findViewById(R.id.earning_amount_editText);
        edtDate = (EditText) findViewById(R.id.earning_date_editText);
        edtDate.setInputType(InputType.TYPE_NULL);
        btnCreate = (Button) findViewById(R.id.create_button);

        edtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        edtLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        btnCreate.setOnClickListener(this);
        edtDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        if (earningToUpdate != null) {
            setTitle("Modifier Revenu");
            edtLabel.setText(earningToUpdate.getLabel());
            edtAmount.setText(Double.toString(earningToUpdate.getAmount()));
            edtDate.setText(dateFormatter.format(earningToUpdate.getDate()));
            btnCreate.setText("Modifier");
        }
    }


    @Override
    public void onClick(View v) {
        if(v == btnCreate) {
            if(valideFields()) {
                if(earningToUpdate != null) {
                    try {
                        earningToUpdate.setLabel(edtLabel.getText().toString());
                        earningToUpdate.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                        earningToUpdate.setDate(dateFormatter.parse(edtDate.getText().toString()));

                        gs.getEarningService().updateEarning(earningToUpdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Earning earning = new Earning();
                        earning.setLabel(edtLabel.getText().toString());
                        earning.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                        earning.setDate(dateFormatter.parse(edtDate.getText().toString()));

                        gs.getEarningService().addEarning(earning);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Intent versEarnings = new Intent(this,Earnings.class);
                versEarnings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(versEarnings);
                finish();
            } else {
                gs.alerter("Données invalides");
            }
        } else if(v == edtDate){
            datePickerDialog.show();
        }

    }

    private boolean valideFields(){
        return notNull(edtLabel.getText().toString()) && notNull(edtAmount.getText().toString())
                && notNull(edtDate.getText().toString()) && Double.parseDouble(edtAmount.getText().toString()) > 0;
    }

    private boolean notNull(String value){
        return (value != null && !("").equals(value));
    }
}
