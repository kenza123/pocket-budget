package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Double oldAmount;
        if (oldBalance == null )
            oldAmount = 0.0;
        else
            oldAmount = oldBalance.getAmount();
        Balance balance = new Balance();
        balance.setDate(date);
        balance.setAmount(oldAmount + amount);
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

    public Map<String, Float> balanceChart(){
        if (balanceDAO.getFirstBalance() != null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -5);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            Date date = balanceDAO.getFirstBalance().getCreatedAt();
            Date sixMonthsAgo = cal.getTime();
            date = least(date, sixMonthsAgo);

            Map<String, Float> result = new LinkedHashMap<>();
            cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            while (cal.getTime().after(date)) {
                Date dateToSave = cal.getTime();
                Log.i(TAG, "result date " + dateToSave);
                result.put(new SimpleDateFormat("MMM").format(dateToSave), getBalanceBeforDate(dateToSave));

                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            return result;
        }
        return null;
    }
    public Float getBalanceBeforDate(Date date){
        Double value = balanceDAO.getBalanceBeforDate(date).getAmount();
        return Float.parseFloat(Double.toString(value));
    }
    public static Date least(Date a, Date b) {
        return a == null ? b : (b == null ? a : (a.before(b) ? b : a));
    }
}
