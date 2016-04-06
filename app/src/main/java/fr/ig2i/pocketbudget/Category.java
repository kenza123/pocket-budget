package fr.ig2i.pocketbudget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenzakhamaily on 24/03/2016.
 */
public class Category {
    String name;
    Double budget;
    Double warningTreshold;
    int bProgress;
    //int pictureId;

    private List<Category> categories;

    public Category() {
    }

    public Category(String name, Double budget, Double warningTreshold, int bProgress) {
        this.name = name;
        this.budget = budget;
        this.warningTreshold = warningTreshold;
        this.bProgress = bProgress;
    }

    public List<Category> getCategories() {
        return categories;
    }

    void initializeData(){
        categories = new ArrayList<Category>();
        categories.add(new Category("Shopping",200.00,150.00, 10));
        categories.add(new Category("Home",300.00,250.00, 20));
        categories.add(new Category("Car",200.00,150.00, 40));
    }

}
