package fr.ig2i.pocketbudget.dao;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class DataBaseDAO {
    private static final String TAG = "DataBaseDAO";

    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context mContext;

    public DataBaseDAO(Context context) {
        this.mContext = context;
        dbHelper = DataBaseHelper.getHelper(mContext);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQL Exception on openning database " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DataBaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
