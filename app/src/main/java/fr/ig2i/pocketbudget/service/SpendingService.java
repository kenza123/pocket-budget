package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.dao.SpendingDAO;
import fr.ig2i.pocketbudget.model.Spending;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class SpendingService {

    private static final String TAG = "SpendingService";
    private SpendingDAO spendingDAO;

    public SpendingService(Context context) {
        spendingDAO = SpendingDAO.getInstance(context);
    }

    public List<Spending> getAllSpendings(){
        List<Spending> spendings = new ArrayList<Spending>();
        spendings.add(new Spending("Robe Zara",100.00));
        spendings.add(new Spending("Pantalon Stradivarius",25.00));
        spendings.add(new Spending("Sac à main Stradivarius",30.00));

        return spendings;
    }

    public List<Spending> getSpendingsByCategoryId(int id) {
        List<Spending> spendings = spendingDAO.getSpendingsByCategoryId(id);
        Log.i(TAG, " Show the spendings");
        for(Spending spending : spendings) {
            Log.i(TAG, spending.toString());
        }
        return spendings;
    }

    public Double countTotalSpendingsOfTheMonth() {
        return spendingDAO.getTotalSpendingsOfTheMonth();
    }

    public Double getTotalSpendingsByCategoryID(int id) {
        return spendingDAO.getTotalSpendingsByCategoryID(id);
    }

    public void addSpending(Spending spending){
        spendingDAO.createSpending(spending);
        Log.i(TAG, "The spending " + spending.toString() + "has been added");
    }

    public void deleteSpending(Spending spending) {
        spendingDAO.deleteSpending(spending);
        Log.i(TAG, "The spending " + spending.toString() + "has been deleted");
    }

    public void updateSpending(Spending spending) {
        if (spendingDAO.getSpendingById(spending.getId()) != null) {
            spendingDAO.updateSpending(spending);
            Log.i(TAG, "The spending " + spending.toString() + "has been updated");
        }
    }

    public String[] getAllLabels() {
        return spendingDAO.getAllLabels();
    }
}
