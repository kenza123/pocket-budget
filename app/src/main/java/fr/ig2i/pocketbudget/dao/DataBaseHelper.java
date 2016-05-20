package fr.ig2i.pocketbudget.dao;

/**
 * Created by ghitakhamaily on 07/04/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "pocketbudgetdb";
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORIE_TABLE = "categorie";
    public static final String SPENDING_TABLE = "spending";
    public static final String EARNING_TABLE = "earning";
    public static final String BALANCE_TABLE = "balance";

    public static final String ID_COLUMN = "id";
    public static final String LABEL_COLUMN = "label";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String DATE_COLUMN = "date";
    public static final String CATEGORIE_BUDGET = "budget";
    public static final String CATEGORIE_WARNING_THRESHOLD = "warning_threshold";
    public static final String SPENDING_CATEGORIE_ID = "id_categ";
    public static final String CREATED_AT_COLUMN = "created_at";
    public static final String DELETED_ON_COLUMN = "deleted_on";

    public static final String CREATE_CATEGORIE_TABLE = "CREATE TABLE "
            + CATEGORIE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LABEL_COLUMN + " TEXT, " + CATEGORIE_BUDGET + " DOUBLE, "
            + CATEGORIE_WARNING_THRESHOLD + " DOUBLE, " + CREATED_AT_COLUMN + " DATE, "
            + DELETED_ON_COLUMN + " DATE )";

    public static final String CREATE_SPENDING_TABLE = "CREATE TABLE "
            + SPENDING_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LABEL_COLUMN + " TEXT, " + AMOUNT_COLUMN + " DOUBLE, "
            + DATE_COLUMN + " DATE, " + SPENDING_CATEGORIE_ID + " INTEGER, "
            + "FOREIGN KEY(" + SPENDING_CATEGORIE_ID + ") REFERENCES "
            + CATEGORIE_TABLE + "(id) " + ")";

    public static final String CREATE_EARNING_TABLE = "CREATE TABLE "
            + EARNING_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LABEL_COLUMN + " TEXT, " + AMOUNT_COLUMN + " DOUBLE, "
            + DATE_COLUMN + " DATE " + ")";

    public static final String CREATE_BALANCE_TABLE = "CREATE TABLE "
            + BALANCE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AMOUNT_COLUMN + " DOUBLE, " + DATE_COLUMN + " DATE, "
            + CREATED_AT_COLUMN + " DATETIME ) ";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORIE_TABLE);
        db.execSQL(CREATE_EARNING_TABLE);
        db.execSQL(CREATE_SPENDING_TABLE);
        db.execSQL(CREATE_BALANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EARNING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPENDING_TABLE);

        // create new tables
        onCreate(db);
    }
}
