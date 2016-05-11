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

    public EarningService(Context context) {
        earningDAO = EarningDAO.getInstance(context);
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
        //modify the balance file
    }

    public void updateEarning(Earning earning){
        if (earningDAO.getEarningById(earning.getId()) != null) {
            earningDAO.updateEarning(earning);
            Log.i(TAG, "The earning " + earning.toString() + "has been updated");
            //modify the balance file
        }
    }

    public void deleteEarning(Earning earning){
        earningDAO.deleteEarning(earning);
        Log.i(TAG, "The earning " + earning.toString() + "has been deleted");
        //modify the  balance file
    }

    public String[] getAllLabels() {
        return earningDAO.getAllLabels();
    }

}
