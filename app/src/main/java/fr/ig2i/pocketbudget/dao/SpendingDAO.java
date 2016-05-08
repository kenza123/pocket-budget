package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.ig2i.pocketbudget.model.Spending;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class SpendingDAO extends DataBaseDAO {

    private static SpendingDAO instance;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.LABEL_COLUMN,
            DataBaseHelper.AMOUNT_COLUMN, DataBaseHelper.DATE_COLUMN,
            DataBaseHelper.SPENDING_CATEGORIE_ID};

    public SpendingDAO(Context context) {
        super(context);
    }

    public static SpendingDAO getInstance(Context context) {
        if(instance == null)
            instance = new SpendingDAO(context);
        return instance;
    }

    public long createSpending(Spending spending) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, spending.getLabel());
        values.put(DataBaseHelper.AMOUNT_COLUMN, spending.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(spending.getDate()));
        values.put(DataBaseHelper.SPENDING_CATEGORIE_ID, spending.getCategory().getId());
        return database.insert(DataBaseHelper.SPENDING_TABLE, null, values);
    }


}
