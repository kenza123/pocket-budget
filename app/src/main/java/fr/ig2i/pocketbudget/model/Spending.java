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
    private Date date;

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
        date = new Date(in.readLong());
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

    public Date getDate() {
        return date;
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

    public void setDate(Date date) {
        this.date = date;
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
        parcel.writeLong(getDate().getTime());
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

    @Override
    public String toString() {
        return "Spending{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", category=" + category +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
