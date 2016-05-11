package fr.ig2i.pocketbudget.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.model.Spending;

public class AddSpending extends AppCompatActivity implements View.OnClickListener  {
    private GlobalState gs;
    private EditText edtLabel;
    private EditText edtAmount;
    private EditText edtDate;
    private Button btnCreate;

    private Spending spendingToUpdate;
    private Category spendingCategory;
    private String TAG = "AddSpending";

    private DatePickerDialog datePickerDialog;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            spendingToUpdate = extras.getParcelable("spending");
            spendingCategory = extras.getParcelable("category");
        }

        gs = (GlobalState) getApplication();

        edtLabel = (EditText) findViewById(R.id.spending_label_editText);
        edtAmount = (EditText) findViewById(R.id.spending_amount_editText);
        edtDate = (EditText) findViewById(R.id.spending_date_editText);
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

        if (spendingToUpdate != null) {
            setTitle("Modifier Dépense");
            edtLabel.setText(spendingToUpdate.getLabel());
            edtAmount.setText(Double.toString(spendingToUpdate.getAmount()));
            edtDate.setText(dateFormatter.format(spendingToUpdate.getDate()));
            btnCreate.setText("Modifier");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnCreate) {
            if (valideFields()) {
                if(spendingToUpdate != null) {
                    try {
                        spendingToUpdate.setLabel(edtLabel.getText().toString());
                        spendingToUpdate.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                        spendingToUpdate.setDate(dateFormatter.parse(edtDate.getText().toString()));

                        gs.getSpendingService().updateSpending(spendingToUpdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Spending spending = new Spending();
                        spending.setLabel(edtLabel.getText().toString());
                        spending.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                        spending.setDate(dateFormatter.parse(edtDate.getText().toString()));
                        spending.setCategory(spendingCategory);

                        gs.getSpendingService().addSpending(spending);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                Intent versCategorySpendings = new Intent(this,CategorySpendings.class);
                versCategorySpendings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                versCategorySpendings.putExtra("category", spendingCategory);
                startActivity(versCategorySpendings);
                finish();
            }
        } else if(v == edtDate) {
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
