package fr.ig2i.pocketbudget.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import fr.ig2i.pocketbudget.model.Category;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class CategoryDAO extends DataBaseDAO {

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";
    private String[] allColumns= { DataBaseHelper.ID_COLUMN, DataBaseHelper.LABEL_COLUMN,
            DataBaseHelper.CATEGORIE_BUDGET, DataBaseHelper.CATEGORIE_WARNING_THRESHOLD};

    public CategoryDAO(Context context) {
        super(context);
    }

    public long createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LABEL_COLUMN, category.getLabel());
        values.put(DataBaseHelper.CATEGORIE_BUDGET, category.getBudget());
        values.put(DataBaseHelper.CATEGORIE_WARNING_THRESHOLD, category.getWarningThreshold());
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

        return category;
    }
}
