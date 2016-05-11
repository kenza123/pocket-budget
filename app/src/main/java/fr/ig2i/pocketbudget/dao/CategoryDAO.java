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
import fr.ig2i.pocketbudget.model.Category;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class CategoryDAO extends DataBaseDAO {

    private static CategoryDAO instance;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd");
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.LABEL_COLUMN,
            DataBaseHelper.CATEGORIE_BUDGET, DataBaseHelper.CATEGORIE_WARNING_THRESHOLD,
            DataBaseHelper.CREATED_AT_COLUMN, DataBaseHelper.DELETED_ON_COLUMN};
    private static final String TAG = "CategoryDAO";

    public CategoryDAO(Context context) {
        super(context);
    }

    public static CategoryDAO getInstance(Context context) {
        if(instance == null)
            instance = new CategoryDAO(context);
        return instance;
    }

    public List<Category> getAllNotDeletedCategories() {
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = database.rawQuery("select * from " + DataBaseHelper.CATEGORIE_TABLE +
                " where " + DataBaseHelper.DELETED_ON_COLUMN + " is null ORDER BY date(" +
                DataBaseHelper.CREATED_AT_COLUMN + ")", null);

        while (cursor.moveToNext()) {
            categories.add(cursorToCategory(cursor));
        }
        cursor.close();
        return categories;
    }

    public long createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, category.getLabel());
        values.put(DataBaseHelper.CATEGORIE_BUDGET, category.getBudget());
        values.put(DataBaseHelper.CATEGORIE_WARNING_THRESHOLD, category.getWarningThreshold());
        values.put(DataBaseHelper.CREATED_AT_COLUMN, formatter.format(new Date()));
        return database.insert(DataBaseHelper.CATEGORIE_TABLE, null, values);
    }

    public long updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, category.getLabel());
        values.put(DataBaseHelper.CATEGORIE_BUDGET, category.getBudget());
        values.put(DataBaseHelper.CATEGORIE_WARNING_THRESHOLD, category.getWarningThreshold());

        long result = database.update(DataBaseHelper.CATEGORIE_TABLE, values,
                WHERE_ID_EQUALS, new String[] { String.valueOf(category.getId()) });
        Log.d("Update Result:", "=" + result);
        return result;
    }

    public int deleteCategory(Category category) {
        return database.delete(DataBaseHelper.CATEGORIE_TABLE,
                WHERE_ID_EQUALS, new String[] { String.valueOf(category.getId())});
    }

    public long markAsDeletedCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.DELETED_ON_COLUMN, formatter.format(new Date()));

        long result = database.update(DataBaseHelper.CATEGORIE_TABLE, values,
                WHERE_ID_EQUALS, new String[] { String.valueOf(category.getId()) });
        Log.d("Delete Result:", "=" + result);
        return result;
    }

    public Double countTheTotalBudget() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);

        Cursor cursor = database.rawQuery("select sum(" + DataBaseHelper.CATEGORIE_BUDGET +
                ") from " + DataBaseHelper.CATEGORIE_TABLE +
                " where " + DataBaseHelper.DELETED_ON_COLUMN + " is null ", null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        Log.e(TAG, String.valueOf(cursor.getDouble(0)));
        return cursor.getDouble(0);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        Cursor cursor = database.query(DataBaseHelper.CATEGORIE_TABLE,
                allColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            categories.add(cursorToCategory(cursor));
        }
        cursor.close();
        return categories;
    }

    public Category getCategoryById(int id) {
        Cursor cursor = database.query(DataBaseHelper.CATEGORIE_TABLE,
                allColumns, WHERE_ID_EQUALS, new String[] { String.valueOf(id)},
                null, null, null);
        if (cursor != null ) {
            cursor.moveToFirst();
        }
        Category earning = cursorToCategory(cursor);
        cursor.close();
        return earning;
    }

    private Category cursorToCategory(Cursor cursor){
        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setLabel(cursor.getString(1));
        category.setBudget(cursor.getDouble(2));
        category.setWarningThreshold(cursor.getDouble(3));
        category.setCreatedAt(formatterDate(cursor.getString(4)));
        category.setDeletedOn(formatterDate(cursor.getString(5)));

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
