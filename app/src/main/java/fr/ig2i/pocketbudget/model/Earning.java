package fr.ig2i.pocketbudget.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ghitakhamaily on 09/04/16.
 */
public class Earning implements Parcelable {

    private int id;
    private String label;
    private Double amount;
    private Date created_at;

    public Earning() {
    }

    public Earning(String label, Double amount) {
        this.label = label;
        this.amount = amount;
    }

    private Earning(Parcel in) {
        id = in.readInt();
        label = in.readString();
        amount = in.readDouble();
        created_at = new Date(in.readLong());;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
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
    }

    public static final Parcelable.Creator<Earning> CREATOR = new Parcelable.Creator<Earning>() {
        public Earning createFromParcel(Parcel in) {
            return new Earning(in);
        }

        public Earning[] newArray(int size) {
            return new Earning[size];
        }
    };
}
