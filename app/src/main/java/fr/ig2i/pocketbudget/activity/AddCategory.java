package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;

import fr.ig2i.pocketbudget.R;

public class AddCategory extends AppCompatActivity implements View.OnClickListener {
    EditText edtLabel;
    EditText edtBudget;
    EditText edtTreshold;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edtLabel = (EditText) findViewById(R.id.category_label_editText);
        edtBudget = (EditText) findViewById(R.id.category_budget_editText);
        edtTreshold = (EditText) findViewById(R.id.category_treshold_editText);
        btnCreate = (Button) findViewById(R.id.create_button);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String label = edtLabel.getText().toString();
        Double budget = Double.parseDouble(edtBudget.getText().toString());
        Double treshold = Double.parseDouble(edtTreshold.getText().toString());
        Intent versSpendings = new Intent(this,Spendings.class);
        //Add new category
        //versSpendings.putExtra("new_categ_label",label);
        //versSpendings.putExtra("new_categ_budget",budget);
        //versSpendings.putExtra("new_categ_treshold",treshold);
        startActivity(versSpendings);
        finish();
    }
}
