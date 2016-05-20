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
    private BalanceService balanceService;

    public SpendingService(Context context) {
        spendingDAO = SpendingDAO.getInstance(context);
        balanceService = new BalanceService(context);
    }

    public List<Spending> getSpendingsOfTheMonthByCategoryId(int id) {
        List<Spending> spendings = spendingDAO.getSpendingsOfTheMonthByCategoryId(id);
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
        return spendingDAO.getTotalSpendingsOfTheMonthByCategoryID(id);
    }

    public void addSpending(Spending spending){
        spendingDAO.createSpending(spending);
        Log.i(TAG, "The spending " + spending.toString() + "has been added");
        balanceService.updateBalance(spending.getDate(), -1 * spending.getAmount());
    }

    public void deleteSpending(Spending spending) {
        spendingDAO.deleteSpending(spending);
        Log.i(TAG, "The spending " + spending.toString() + "has been deleted");
        balanceService.updateBalance(spending.getDate(), spending.getAmount());
    }

    public void updateSpending(Spending spending) {
        Spending oldSpending = spendingDAO.getSpendingById(spending.getId());
        if (oldSpending != null) {
            spendingDAO.updateSpending(spending);
            Log.i(TAG, "The spending " + spending.toString() + "has been updated");
            balanceService.updateBalance(spending.getDate(),
                    oldSpending.getAmount() - spending.getAmount());
        }
    }

    public String[] getAllLabels() {
        return spendingDAO.getAllLabels();
    }
}
