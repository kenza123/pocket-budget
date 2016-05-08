package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ig2i.pocketbudget.model.Earning;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class EarningDAO extends DataBaseDAO {

    private static EarningDAO instance;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd");
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.LABEL_COLUMN,
            DataBaseHelper.AMOUNT_COLUMN, DataBaseHelper.DATE_COLUMN};
    private static final String TAG = "EarningDAO";

    public EarningDAO(Context context) {
        super(context);
    }

    public static EarningDAO getInstance(Context context) {
        if(instance == null)
            instance = new EarningDAO(context);
        return instance;
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
        Log.i("Update Result:", "=" + result);
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

    public List<Earning> getAllEarningsOfTheMonth() {
        List<Earning> earnings = new ArrayList<Earning>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelper.EARNING_TABLE +
                " where " + DataBaseHelper.DATE_COLUMN + " >= '" + formatter.format(c.getTime()) +
                "' ORDER BY date(" + DataBaseHelper.DATE_COLUMN + ")", null);

        while (cursor.moveToNext()) {
            Earning earning = cursorToEarning(cursor);
            earnings.add(earning);
        }
        cursor.close();
        return earnings;
    }

    public Double countSumEarningsOfTheMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        Cursor cursor = database.rawQuery("select sum(" + DataBaseHelper.AMOUNT_COLUMN +
                ") from " + DataBaseHelper.EARNING_TABLE +
                " where " + DataBaseHelper.DATE_COLUMN + " >= '" + formatter.format(c.getTime()) + "'", null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        return cursor.getDouble(0);
    }

    public Earning getEarningById(int id) {
        Cursor cursor = database.query(DataBaseHelper.EARNING_TABLE,
                allColumns, DataBaseHelper.DATE_COLUMN + "", new String[] { String.valueOf(id)},
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
        try {
            earning.setId(cursor.getInt(0));
            earning.setLabel(cursor.getString(1));
            earning.setAmount(cursor.getDouble(2));
            earning.setDate(formatter.parse(cursor.getString(3)));
        } catch (ParseException e) {
            Log.e(TAG, "error while parsing the date");
            e.printStackTrace();
        }
        return earning;
    }
}
