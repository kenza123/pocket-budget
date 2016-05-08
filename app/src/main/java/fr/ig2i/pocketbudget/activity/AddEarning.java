package fr.ig2i.pocketbudget.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Earning;
import fr.ig2i.pocketbudget.service.EarningService;

public class AddEarning extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    private EditText edtLabel;
    private EditText edtAmount;
    private EditText edtDate;
    private Button btnCreate;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gs = (GlobalState) getApplication();
        setContentView(R.layout.activity_add_earning);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        edtLabel = (EditText) findViewById(R.id.category_label_editText);
        edtAmount = (EditText) findViewById(R.id.category_amount_editText);
        edtDate = (EditText) findViewById(R.id.category_date_editText);
        edtDate.setInputType(InputType.TYPE_NULL);
        btnCreate = (Button) findViewById(R.id.create_button);

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
    }


    @Override
    public void onClick(View v) {
        if(v == btnCreate){
            if(valideFields()){
                Earning earning = new Earning();
                earning.setLabel(edtLabel.getText().toString());
                earning.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                earning.setDate(new Date(edtDate.getText().toString()));

                EarningService earningService = new EarningService(gs.getApplicationContext());
                earningService.addEarning(earning);
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
