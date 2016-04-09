package fr.ig2i.pocketbudget.service;

import java.util.ArrayList;
import java.util.List;

import fr.ig2i.pocketbudget.model.Earning;

/**
 * Created by kenzakhamaily on 09/04/2016.
 */
public class EarningService {

    public List<Earning> getAllEarnings() {
        List<Earning> earnings = new ArrayList<Earning>();
        earnings.add(new Earning("Salaire",6000.00));
        earnings.add(new Earning("Prime",300.00));
        earnings.add(new Earning("Cadeaux",200.00));

        return earnings;
    }
}
