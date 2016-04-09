package fr.ig2i.pocketbudget.service;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.model.Category;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class CategoryService {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Shopping",200.00,150.00, 10));
        categories.add(new Category("Home",300.00,250.00, 20));
        categories.add(new Category("Car",200.00,150.00, 40));

        return categories;
    }
}
