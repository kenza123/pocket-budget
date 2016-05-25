package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ig2i.pocketbudget.model.Balance;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class BalanceDAO extends DataBaseDAO {

    private static BalanceDAO instance;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.AMOUNT_COLUMN,
            DataBaseHelper.DATE_COLUMN, DataBaseHelper.CREATED_AT_COLUMN};
    private static final String TAG = "BalanceDAO";

    public BalanceDAO(Context context) {
        super(context);
    }

    public static BalanceDAO getInstance(Context context) {
        if(instance == null)
            instance = new BalanceDAO(context);
        return instance;
    }

    public long createBalance(Balance balance) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.AMOUNT_COLUMN, balance.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(balance.getDate()));
        values.put(DataBaseHelper.CREATED_AT_COLUMN, dateTimeFormatter.format(balance.getCreatedAt()));
        return database.insert(DataBaseHelper.BALANCE_TABLE, null, values);
    }

    public long updateBalance(Balance balance) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.AMOUNT_COLUMN, balance.getAmount());
        values.put(DataBaseHelper.DATE_COLUMN, formatter.format(balance.getDate()));
        values.put(DataBaseHelper.CREATED_AT_COLUMN, dateTimeFormatter.format(balance.getCreatedAt()));

        long result = database.update(DataBaseHelper.BALANCE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(balance.getId())});
        Log.i("Update Result:", "=" + result);
        return result;
    }

    public int deleteBalance(Balance balance) {
        return database.delete(DataBaseHelper.BALANCE_TABLE,
                WHERE_ID_EQUALS, new String[]{balance.getId() + ""});
    }

    public Balance getLastBalance() {
        Cursor cursor = database.rawQuery("select * from " + DataBaseHelper.BALANCE_TABLE +
                " ORDER BY " + DataBaseHelper.CREATED_AT_COLUMN + " DESC LIMIT 1", null);
        Balance balance = null;
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            balance = cursorToBalance(cursor);
        }
        cursor.close();
        return balance;
    }

    public Balance getFirstBalance() {
        Cursor cursor = database.rawQuery("select * from " + DataBaseHelper.BALANCE_TABLE +
                " ORDER BY " + DataBaseHelper.CREATED_AT_COLUMN + " ASC LIMIT 1", null);
        Balance balance = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            balance = cursorToBalance(cursor);
        }
        cursor.close();
        return balance;
    }

    public Balance getBalanceBeforDate(Date date) {
        Cursor cursor = database.rawQuery("select * from " + DataBaseHelper.BALANCE_TABLE +
                " WHERE " + DataBaseHelper.DATE_COLUMN + " <= '" + formatter.format(date) +
                "' ORDER BY " + DataBaseHelper.DATE_COLUMN + " DESC LIMIT 1", null);

        Balance balance = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            balance = cursorToBalance(cursor);
        }
        cursor.close();
        return balance;
    }

    public List<Balance> getAllBalances() {
        List<Balance> balances = new ArrayList<Balance>();
        Cursor cursor = database.query(DataBaseHelper.BALANCE_TABLE,
                allColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Balance balance = cursorToBalance(cursor);
            balances.add(balance);
        }
        cursor.close();
        return balances;
    }

    private Balance cursorToBalance(Cursor cursor){
        Balance balance = new Balance();
        try {
            balance.setId(cursor.getInt(0));
            balance.setAmount(cursor.getDouble(1));
            balance.setDate(formatter.parse(cursor.getString(2)));
            Date date = dateTimeFormatter.parse(cursor.getString(3));
            balance.setCreatedAt(new Timestamp(date.getTime()));
        } catch (ParseException e) {
            Log.e(TAG, "error while parsing the date");
            e.printStackTrace();
        }
        return balance;
    }
}
