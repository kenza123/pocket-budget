package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ig2i.pocketbudget.model.Category;
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
    private String allColumns = "spend. " + DataBaseHelper.ID_COLUMN + ", spend." + DataBaseHelper.LABEL_COLUMN +
            ", spend." + DataBaseHelper.AMOUNT_COLUMN + ", spend." + DataBaseHelper.DATE_COLUMN +
            ", categ." + DataBaseHelper.ID_COLUMN + ", categ." + DataBaseHelper.LABEL_COLUMN +
            ", categ." + DataBaseHelper.CATEGORIE_BUDGET + ", categ." + DataBaseHelper.CATEGORIE_WARNING_THRESHOLD +
            ", categ." + DataBaseHelper.CREATED_AT_COLUMN + ", categ." + DataBaseHelper.DELETED_ON_COLUMN;
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
                WHERE_ID_EQUALS, new String[]{spending.getId() + ""});
    }

    public Spending getSpendingById(int id) {
        String query = "select " +  allColumns + " from " + DataBaseHelper.SPENDING_TABLE + " spend, " +
                DataBaseHelper.CATEGORIE_TABLE + " categ where spend." + DataBaseHelper.SPENDING_CATEGORIE_ID +
                " = categ." + DataBaseHelper.ID_COLUMN + " and spend." + DataBaseHelper.ID_COLUMN +
                " = " + id;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        Spending spending = cursorToSpending(cursor);
        cursor.close();
        return spending;
    }

    public List<Spending> getSpendingsByCategoryId(int id) {
        List<Spending> spendings = new ArrayList<Spending>();
        String query = "select " +  allColumns + " from " + DataBaseHelper.SPENDING_TABLE + " spend, " +
                DataBaseHelper.CATEGORIE_TABLE + " categ where spend." + DataBaseHelper.SPENDING_CATEGORIE_ID +
                " = categ." + DataBaseHelper.ID_COLUMN + " and spend." + DataBaseHelper.SPENDING_CATEGORIE_ID +
                " = " + id + " ORDER BY date(spend." + DataBaseHelper.DATE_COLUMN + ")";

        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            spendings.add(cursorToSpending(cursor));
        }
        cursor.close();
        return spendings;
    }

    public Double getTotalSpendingsOfTheMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        Cursor cursor = database.rawQuery("select sum(spending." + DataBaseHelper.AMOUNT_COLUMN +
                ") FROM " + DataBaseHelper.SPENDING_TABLE + " spending " +
                "INNER JOIN " + DataBaseHelper.CATEGORIE_TABLE + " category " +
                "ON category." + DataBaseHelper.ID_COLUMN + " = spending." + DataBaseHelper.SPENDING_CATEGORIE_ID +
                " WHERE category." + DataBaseHelper.DELETED_ON_COLUMN + " is null " +
                "AND spending." + DataBaseHelper.DATE_COLUMN + " >= '" + formatter.format(c.getTime()) + "'",
                null);

        if (cursor != null ) {
            cursor.moveToFirst();
        }
        return cursor.getDouble(0);
    }

    public Double getTotalSpendingsByCategoryID(int idCategory) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        Cursor cursor = database.rawQuery("select sum(" + DataBaseHelper.AMOUNT_COLUMN +
                        ") FROM " + DataBaseHelper.SPENDING_TABLE +
                        " WHERE " + DataBaseHelper.SPENDING_CATEGORIE_ID + " = " + idCategory +
                        " AND " + DataBaseHelper.DATE_COLUMN + " >= '" + formatter.format(c.getTime()) + "'",
                null);

        if (cursor != null ) {
            cursor.moveToFirst();
        }
        return cursor.getDouble(0);
    }

    public String[] getAllLabels() {
        List<String> labels = new ArrayList<String>();
        Cursor cursor = database.query(DataBaseHelper.SPENDING_TABLE,
                new String[]{DataBaseHelper.LABEL_COLUMN}, null, null, DataBaseHelper.LABEL_COLUMN,
                null, null);

        while (cursor.moveToNext()) {
            String Label = cursor.getString(0);
            labels.add(Label);
        }
        cursor.close();
        String[] labelsArr = new String[labels.size()];
        return labels.toArray(labelsArr);
    }

    private Spending cursorToSpending(Cursor cursor) {
        Spending spending = new Spending();
        try {
            spending.setId(cursor.getInt(0));
            spending.setLabel(cursor.getString(1));
            spending.setAmount(cursor.getDouble(2));
            spending.setDate(formatter.parse(cursor.getString(3)));
            spending.setCategory(cursorToCategory(cursor));
        } catch (ParseException e) {
            Log.e(TAG, "error while parsing the date");
            e.printStackTrace();
        }
        return spending;
    }

    private Category cursorToCategory(Cursor cursor){
        Category category = new Category();
        category.setId(cursor.getInt(4));
        category.setLabel(cursor.getString(5));
        category.setBudget(cursor.getDouble(6));
        category.setWarningThreshold(cursor.getDouble(7));
        category.setCreatedAt(formatterDate(cursor.getString(8)));
        category.setDeletedOn(formatterDate(cursor.getString(9)));

        return category;
    }

    private Date formatterDate(String date){
        if (date == null) {
            return null;
        } else {
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                Log.e(TAG, "error while parsing the date");
                e.printStackTrace();
                return null;
            }
        }
    }

}
