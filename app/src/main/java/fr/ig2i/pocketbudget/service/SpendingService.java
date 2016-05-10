package fr.ig2i.pocketbudget.service;

import android.content.Context;

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

    public SpendingService() {
        //spendingDAO = SpendingDAO.getInstance(context);
    }

    public List<Spending> getAllSpendings(){
        List<Spending> spendings = new ArrayList<Spending>();
        spendings.add(new Spending("Robe Zara",100.00));
        spendings.add(new Spending("Pantalon Stradivarius",25.00));
        spendings.add(new Spending("Sac à main Stradivarius",30.00));

        return spendings;
    }
}
