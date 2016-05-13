package fr.ig2i.pocketbudget.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.model.Earning;
import fr.ig2i.pocketbudget.model.Spending;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    private Switch itemSwitch;
    private TextView tvCategory;
    private Spinner spinCategory;
    private AutoCompleteTextView edtLabel;
    private EditText edtAmount;
    private EditText edtDate;
    private Button btnCreate;
    private Context context;

    private String TAG = "AddItem";

    private DatePickerDialog datePickerDialog;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        context = this;
        gs = (GlobalState) getApplication();

        itemSwitch = (Switch) findViewById(R.id.item_switch);
        spinCategory = (Spinner) findViewById(R.id.item_category_spinner);
        edtLabel = (AutoCompleteTextView) findViewById(R.id.item_label_editText);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gs.getSpendingService().getAllLabels());
        edtLabel.setAdapter(adapter);
        edtLabel.setThreshold(2);

        List<Category> categories = gs.getCategoryService().getAllNotDeletedCategoriesOrdered();
        categories.add(new Category("Nouvelle catégorie"));
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(dataAdapter);
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category categorySelected = (Category) parent.getItemAtPosition(position);
                if (categorySelected.getLabel().equals("Nouvelle catégorie")){
                    Intent versAddCategory = new Intent(context, AddCategory.class);
                    versAddCategory.putExtra("intentFromActivity", "AddItem");
                    startActivity(versAddCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    protected void onRestart() {
        super.onRestart();

        context = this;
        gs = (GlobalState) getApplication();

        List<Category> categories = gs.getCategoryService().getAllNotDeletedCategoriesOrdered();
        categories.add(new Category("Nouvelle catégorie"));
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(dataAdapter);
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category categorySelected = (Category) parent.getItemAtPosition(position);
                if (categorySelected.getLabel().equals("Nouvelle catégorie")){
                    Intent versAddCategory = new Intent(context, AddCategory.class);
                    versAddCategory.putExtra("intentFromActivity", "AddItem");
                    startActivity(versAddCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.create_button :
                if(valideFields()) {
                    if (itemSwitch.getText().equals("Dépense")) {
                        try {
                            Spending spending = new Spending();
                            spending.setLabel(edtLabel.getText().toString());
                            spending.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                            spending.setDate(dateFormatter.parse(edtDate.getText().toString()));
                            spending.setCategory((Category) spinCategory.getSelectedItem());

                            gs.getSpendingService().addSpending(spending);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Intent versCategorySpendings = new Intent(this,CategorySpendings.class);
                        versCategorySpendings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        versCategorySpendings.putExtra("category", (Category) spinCategory.getSelectedItem());
                        startActivity(versCategorySpendings);
                        finish();
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
                        Intent versEarnings = new Intent(this,Earnings.class);
                        versEarnings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(versEarnings);
                        finish();
                    }
                } else {
                    gs.alerter("Données invalides");
                }
                break;
            case R.id.item_date_editText :
                datePickerDialog.show();
                break;
            case R.id.item_switch :
                tvCategory = (TextView) findViewById(R.id.item_category);
                if (itemSwitch.getText().equals("Dépense")) {
                    itemSwitch.setText("Revenu");
                    spinCategory.setVisibility(View.GONE);
                    tvCategory.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_dropdown_item_1line, gs.getEarningService().getAllLabels());
                    edtLabel.setAdapter(adapter);
                    edtLabel.setThreshold(2);
                } else {
                    itemSwitch.setText("Dépense");
                    spinCategory.setVisibility(View.VISIBLE);
                    tvCategory.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_dropdown_item_1line, gs.getSpendingService().getAllLabels());
                    edtLabel.setAdapter(adapter);
                    edtLabel.setThreshold(2);
                }
                break;
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
