package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ig2i.pocketbudget.model.Earning;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class EarningDAO extends DataBaseDAO {

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.LABEL_COLUMN,
            DataBaseHelper.AMOUNT_COLUMN, DataBaseHelper.DATE_COLUMN};

    public EarningDAO(Context context) {
        super(context);
    }

    public long createEarning(Earning earning) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, earning.getLabel());
        values.put(DataBaseHelper.AMOUNT_COLUMN, earning.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(earning.getDate()));
        return database.insert(DataBaseHelper.EARNING_TABLE, null, values);
    }

    public long updateEarning(Earning earning) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, earning.getLabel());
        values.put(DataBaseHelper.AMOUNT_COLUMN, earning.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(earning.getDate()));

        long result = database.update(DataBaseHelper.EARNING_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(earning.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int deleteEarning(Earning earning) {
        return database.delete(DataBaseHelper.EARNING_TABLE,
                WHERE_ID_EQUALS, new String[] { earning.getId() + "" });
    }

    public List<Earning> getAllEarnings() {
        List<Earning> earnings = new ArrayList<Earning>();
        Cursor cursor = database.query(DataBaseHelper.EARNING_TABLE,
                allColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Earning earning = cursorToEarning(cursor);
            earnings.add(earning);
        }
        cursor.close();
        return earnings;
    }

    public Earning getEarningById(int id) {
        Cursor cursor = database.query(DataBaseHelper.EARNING_TABLE,
                allColumns, WHERE_ID_EQUALS, new String[] { String.valueOf(id)},
                null, null, null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        Earning earning = cursorToEarning(cursor);
        cursor.close();
        return earning;
    }

    private Earning cursorToEarning(Cursor cursor){
        Earning earning = new Earning();
        earning.setId(cursor.getInt(0));
        earning.setLabel(cursor.getString(1));
        earning.setAmount(cursor.getDouble(2));
        earning.setDate(new Date(cursor.getLong(3)));

        return earning;
    }
}
