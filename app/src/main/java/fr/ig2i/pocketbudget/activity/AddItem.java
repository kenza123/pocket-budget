package fr.ig2i.pocketbudget.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    private Switch itemSwitch;
    private TextView tvCategory;
    private EditText edtCategory;
    private EditText edtLabel;
    private EditText edtAmount;
    private EditText edtDate;
    private Button btnCreate;

    private String TAG = "AddItem";

    private DatePickerDialog datePickerDialog;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        gs = (GlobalState) getApplication();

        itemSwitch = (Switch) findViewById(R.id.item_switch);
        edtCategory = (EditText) findViewById(R.id.item_category_editText);
        edtLabel = (EditText) findViewById(R.id.item_label_editText);
        edtAmount = (EditText) findViewById(R.id.item_amount_editText);
        edtDate = (EditText) findViewById(R.id.item_date_editText);
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

        edtCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        itemSwitch.setOnClickListener(this);

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
        switch(v.getId()) {
            case R.id.create_button :
                break;
            case R.id.item_date_editText :
                datePickerDialog.show();
                break;
            case R.id.item_switch :
                itemSwitch = (Switch) findViewById(R.id.item_switch);
                edtCategory = (EditText) findViewById(R.id.item_category_editText);
                tvCategory = (TextView) findViewById(R.id.item_category);
                if (itemSwitch.getText().equals("Dépense")) {
                    itemSwitch.setText("Revenu");
                    edtCategory.setVisibility(View.GONE);
                    tvCategory.setVisibility(View.GONE);
                } else {
                    itemSwitch.setText("Dépense");
                    edtCategory.setVisibility(View.VISIBLE);
                    tvCategory.setVisibility(View.GONE);
                }

                break;
        }
    }
}
