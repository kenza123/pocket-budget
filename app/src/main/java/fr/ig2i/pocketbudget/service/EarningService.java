package fr.ig2i.pocketbudget.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
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
        earningDAO = new EarningDAO(context);
    }

    public List<Earning> getAllEarningsOfTheMonth(){
        List<Earning> earnings = earningDAO.getAllEarnings();

        Log.i(TAG, " Show the earnings");
        for(Earning earning : earnings) {
            Log.i(TAG, earning.toString());
        }
        //create an request in dao
        //check if null
        return earnings;
    }

    public double countSumEarningsOfTheMonth(){
        //create an request in dao
        return 0.0;
    }

    public void addEarning(Earning earning){
        earningDAO.createEarning(earning);
        Log.i(TAG, "The earning " + earning.toString() + "has been added");
        //modify the balance file
    }

    public void updateEarning(Earning earning){
        if (earningDAO.getEarningById(earning.getId()) != null) {
            earningDAO.updateEarning(earning);
            //modify the balance file
        }
    }

    public void deleteEarning(Earning earning){
        //check if the earning exist??
        earningDAO.deleteEarning(earning);
        Log.i(TAG, "The earning " + earning.toString() + "has been deleted");

        List<Earning> earnings = earningDAO.getAllEarnings();
        Log.i(TAG, " Show the earnings");
        for(Earning item : earnings) {
            Log.i(TAG, item.toString());
        }
        //modify the  balance file
    }

}
