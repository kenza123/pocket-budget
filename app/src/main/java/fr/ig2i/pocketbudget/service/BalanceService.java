package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Date;

import fr.ig2i.pocketbudget.dao.BalanceDAO;
import fr.ig2i.pocketbudget.model.Balance;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class BalanceService {

    private static final String TAG = "BalanceService";
    private BalanceDAO balanceDAO;

    public BalanceService(Context context) {
        balanceDAO = BalanceDAO.getInstance(context);
    }

    public void createBalance(Balance balance){
        balanceDAO.createBalance(balance);
        Log.i(TAG, "The balance " + balance.toString() + "has been created");
    }

    public void updateBalance(Date date, Double amount){
        Date currentDate = new Date();
        Balance oldBalance = balanceDAO.getLastBalance();
        Balance balance = new Balance();
        balance.setDate(date);
        balance.setAmount(oldBalance.getAmount() + amount);
        balance.setCreatedAt(new Timestamp(currentDate.getTime()));
        balanceDAO.createBalance(balance);
        Log.i(TAG, "The balance " + balance.toString() + "has been created");
    }

    public double getBalanceAmount(){
        if(balanceDAO.getLastBalance() != null) {
            return balanceDAO.getLastBalance().getAmount();
        }
        return 0;
    }
}
