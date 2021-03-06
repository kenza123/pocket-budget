package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.dao.CategoryDAO;
import fr.ig2i.pocketbudget.model.Category;
import fr.ig2i.pocketbudget.model.Spending;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class CategoryService {

    private static final String TAG = "CategoryService";
    private CategoryDAO categoryDAO;
    private SpendingService spendingService;

    public CategoryService(Context context) {
        categoryDAO = CategoryDAO.getInstance(context);
        spendingService = new SpendingService(context);
    }

    public List<Category> getAllNotDeletedCategories(){
        List<Category> categories = categoryDAO.getAllNotDeletedCategories();

        Log.i(TAG, " Show the categories");
        for(Category category : categories) {
            Log.i(TAG, category.toString());
        }
        return categories;
    }

    public List<Category> getAllNotDeletedCategoriesOrdered(){
        List<Category> categories = categoryDAO.getAllNotDeletedCategoriesOrdered();
        Log.i(TAG, " Show the categories");
        for(Category category : categories) {
            Log.i(TAG, category.toString());
        }
        return categories;
    }

    public List<Category> getTwoMonthsCategories(){
        List<Category> categories = categoryDAO.getTwoMonthsCategories();
        Log.i(TAG, " Show the categories");
        for(Category category : categories) {
            Log.i(TAG, category.toString());
        }
        return categories;
    }

    public double countTheTotalBudget(){
        return categoryDAO.countTheTotalBudget();
    }

    public void addCategory(Category category){
        categoryDAO.createCategory(category);
        Log.i(TAG, "The category " + category.toString() + "has been added");
    }

    public void updateCategory(Category category){
        if (categoryDAO.getCategoryById(category.getId()) != null) {
            categoryDAO.updateCategory(category);
            Log.i(TAG, "The category " + category.toString() + "has been updated");
        }
    }
    public void deleteCategory(Category category){
        categoryDAO.markAsDeletedCategory(category);
        List<Spending> spendings = spendingService.getSpendingsOfTheMonthByCategoryId(category.getId());
        for(Spending spending : spendings) {
            spendingService.deleteSpending(spending);
        }
        Log.i(TAG, "The category " + category.toString() + "has been deleted");
        //update the balance: reduce the spendings of this category
    }
}
