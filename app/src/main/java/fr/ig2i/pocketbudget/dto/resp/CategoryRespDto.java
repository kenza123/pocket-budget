package fr.ig2i.pocketbudget.dto.resp;

import android.annotation.SuppressLint;

import fr.ig2i.pocketbudget.model.Category;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
@SuppressLint("ParcelCreator")
public class CategoryRespDto extends Category{

    private int bProgress;
    private double depense;

    public CategoryRespDto() {
    }

    public CategoryRespDto(String label, Double budget, Double warningTreshold, int bProgress) {
        super(label, budget, warningTreshold);
        this.bProgress = bProgress;
    }

    public int getbProgress() {
        return bProgress;
    }

    public double getDepense() {
        return depense;
    }

    public void setbProgress(int bProgress) {
        this.bProgress = bProgress;
    }

    public void setDepense(double depense) {
        this.depense = depense;
    }
}

