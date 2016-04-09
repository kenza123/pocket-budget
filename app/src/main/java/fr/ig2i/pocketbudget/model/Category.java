package fr.ig2i.pocketbudget.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kenzakhamaily on 24/03/2016.
 */
public class Category implements Parcelable{
    private int id;
    private String label;
    private Double budget;
    private Double warningTreshold;

    int bProgress;
    //int pictureId;

    public Category() {
    }

    public Category(String label, Double budget, Double warningTreshold) {
        this.label = label;
        this.budget = budget;
        this.warningTreshold = warningTreshold;
    }

    public Category(String label, Double budget, Double warningTreshold, int bProgress) {
        this.label = label;
        this.budget = budget;
        this.warningTreshold = warningTreshold;
        this.bProgress = bProgress;
    }

    private Category(Parcel in) {
        id = in.readInt();
        label = in.readString();
        budget = in.readDouble();
        warningTreshold = in.readDouble();
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public Double getBudget() {
        return budget;
    }

    public Double getWarningTreshold() {
        return warningTreshold;
    }

    public int getbProgress() {
        return bProgress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setWarningTreshold(Double warningTreshold) {
        this.warningTreshold = warningTreshold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getLabel());
        parcel.writeDouble(getBudget());
        parcel.writeDouble(getWarningTreshold());
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}