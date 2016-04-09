package fr.ig2i.pocketbudget.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/**
 * Created by kenzakhamaily on 07/04/2016.
 */
public class Spending implements Parcelable {
    private int id;
    private String label;
    private Category category;
    private Double amount;
    private Date created_at;

    public Spending() {
    }

    public Spending(String label, Double amount) {
        this.label = label;
        this.amount = amount;
    }

    private Spending(Parcel in) {
        id = in.readInt();
        label = in.readString();
        amount = in.readDouble();
        created_at = new Date(in.readLong());
        category = in.readParcelable(Category.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getLabel());
        parcel.writeDouble(getAmount());
        parcel.writeLong(getCreated_at().getTime());
        parcel.writeParcelable(getCategory(), flags);
    }

    public static final Parcelable.Creator<Spending> CREATOR = new Parcelable.Creator<Spending>() {
        public Spending createFromParcel(Parcel in) {
            return new Spending(in);
        }

        public Spending[] newArray(int size) {
            return new Spending[size];
        }
    };
}
