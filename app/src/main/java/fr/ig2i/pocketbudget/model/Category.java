package fr.ig2i.pocketbudget.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by kenzakhamaily on 24/03/2016.
 */
public class Category implements Parcelable{
    private int id;
    private String label;
    private Double budget;
    private Double warningThreshold;
    private Date createdAt;
    private Date deletedOn;

    private int depenseProgress;

    public Category() {
    }

    public Category(String label) {
        this.label = label;
    }

    private Category(Parcel in) {
        id = in.readInt();
        label = in.readString();
        budget = in.readDouble();
        warningThreshold = in.readDouble();
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

    public Double getWarningThreshold() {
        return warningThreshold;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public int getDepenseProgress() {
        return 50;
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

    public void setWarningThreshold(Double warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public void setDepenseProgress(int depenseProgress) {
        this.depenseProgress = depenseProgress;
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
        parcel.writeDouble(getWarningThreshold());
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", budget=" + budget +
                ", warningThreshold=" + warningThreshold +
                ", createdAt=" + createdAt +
                ", deletedOn=" + deletedOn +
                ", depenseProgress=" + depenseProgress +
                '}';
    }
}
