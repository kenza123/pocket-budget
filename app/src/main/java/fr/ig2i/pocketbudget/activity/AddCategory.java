package fr.ig2i.pocketbudget.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.ig2i.pocketbudget.GlobalState;
import fr.ig2i.pocketbudget.R;
import fr.ig2i.pocketbudget.model.Category;

public class AddCategory extends AppCompatActivity implements View.OnClickListener {

    private GlobalState gs;
    EditText edtLabel;
    EditText edtBudget;
    EditText edtTreshold;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        gs = (GlobalState) getApplication();
        edtLabel = (EditText) findViewById(R.id.category_label_editText);
        edtBudget = (EditText) findViewById(R.id.category_budget_editText);
        edtTreshold = (EditText) findViewById(R.id.category_treshold_editText);
        btnCreate = (Button) findViewById(R.id.create_button);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(valideFields()) {
            Category category = new Category();
            category.setLabel(edtLabel.getText().toString());
            category.setBudget(Double.parseDouble(edtBudget.getText().toString()));
            category.setWarningThreshold(Double.parseDouble(edtTreshold.getText().toString()));

            gs.getCategoryService().addCategory(category);

            Intent versSpendings = new Intent(this,Categories.class);
            versSpendings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(versSpendings);
            finish();
        } else {
        gs.alerter("Données invalides");
        }
    }

    private boolean valideFields(){
        return notNull(edtLabel.getText().toString()) && notNull(edtBudget.getText().toString())
                && notNull(edtTreshold.getText().toString()) && Double.parseDouble(edtBudget.getText().toString()) > 0
                && Double.parseDouble(edtTreshold.getText().toString()) > 0;
    }

    private boolean notNull(String value){
        return (value != null && !("").equals(value));
    }
}
