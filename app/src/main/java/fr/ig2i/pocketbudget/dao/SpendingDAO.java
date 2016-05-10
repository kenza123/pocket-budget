package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
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
    private static final String TAG = "SpendingDAO";

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

    public long updateSpending(Spending spending) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, spending.getLabel());
        values.put(DataBaseHelper.AMOUNT_COLUMN, spending.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(spending.getDate()));
        values.put(DataBaseHelper.SPENDING_CATEGORIE_ID, spending.getCategory().getId());

        long result = database.update(DataBaseHelper.SPENDING_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(spending.getId()) });
        Log.i("Update Result:", "=" + result);
        return result;
    }

    public int deleteSpending(Spending spending) {
        return database.delete(DataBaseHelper.SPENDING_TABLE,
                WHERE_ID_EQUALS, new String[] { spending.getId() + "" });
    }

    public Spending getSpendingById(int id) {
        Cursor cursor = database.query(DataBaseHelper.SPENDING_TABLE,
                allColumns, WHERE_ID_EQUALS, new String[] { String.valueOf(id)},
                null, null, null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        Spending spending = cursorToSpending(cursor);
        cursor.close();
        return spending;
    }

    private Spending cursorToSpending(Cursor cursor) {
        Spending spending = new Spending();
        try {
            spending.setId(cursor.getInt(0));
            spending.setLabel(cursor.getString(1));
            spending.setAmount(cursor.getDouble(2));
            spending.setDate(formatter.parse(cursor.getString(3)));
        } catch (ParseException e) {
            Log.e(TAG, "error while parsing the date");
            e.printStackTrace();
        }
        return spending;
    }

}
