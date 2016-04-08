package fr.ig2i.pocketbudget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenzakhamaily on 07/04/2016.
 */
public class Spending {
    String name;
    Double price;

    private List<Spending> categorySpendings;

    public Spending() {
    }

    public Spending(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public List<Spending> getCategorySpendings() {
        return categorySpendings;
    }

    void initializeData(){
        categorySpendings = new ArrayList<Spending>();
        categorySpendings.add(new Spending("Robe Zara",100.00));
        categorySpendings.add(new Spending("Pantalon Stradivarius",25.00));
        categorySpendings.add(new Spending("Sac à main Stradivarius",30.00));

    }
}
