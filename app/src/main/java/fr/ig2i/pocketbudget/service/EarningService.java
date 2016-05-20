package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import fr.ig2i.pocketbudget.dao.EarningDAO;
import fr.ig2i.pocketbudget.model.Earning;

/**
 * Created by kenzakhamaily on 09/04/2016.
 */
public class EarningService {

    private static final String TAG = "EarningService";
    private EarningDAO earningDAO;
    private BalanceService balanceService;

    public EarningService(Context context) {
        earningDAO = EarningDAO.getInstance(context);
        balanceService = new BalanceService(context);
    }

    public List<Earning> getAllEarningsOfTheMonth(){
        List<Earning> earnings = earningDAO.getAllEarningsOfTheMonth();

        Log.i(TAG, " Show the earnings");
        for(Earning earning : earnings) {
            Log.i(TAG, earning.toString());
        }
        return earnings;
    }

    public double countSumEarningsOfTheMonth(){
        return earningDAO.countSumEarningsOfTheMonth();
    }

    public void addEarning(Earning earning){
        earningDAO.createEarning(earning);
        Log.i(TAG, "The earning " + earning.toString() + "has been added");
        balanceService.updateBalance(earning.getDate(), earning.getAmount());
    }

    public void updateEarning(Earning earning){
        Earning oldEarning = earningDAO.getEarningById(earning.getId());
        if (oldEarning != null) {
            earningDAO.updateEarning(earning);
            Log.i(TAG, "The earning " + earning.toString() + "has been updated");
            balanceService.updateBalance(earning.getDate(),
                    earning.getAmount() - oldEarning.getAmount());
        }
    }

    public void deleteEarning(Earning earning){
        earningDAO.deleteEarning(earning);
        Log.i(TAG, "The earning " + earning.toString() + "has been deleted");
        balanceService.updateBalance(earning.getDate(), -1 * earning.getAmount());
    }

    public String[] getAllLabels() {
        return earningDAO.getAllLabels();
    }

}
