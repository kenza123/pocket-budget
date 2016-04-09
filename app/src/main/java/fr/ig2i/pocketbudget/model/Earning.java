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
    private Date date;

    public Earning() {
    }

    public Earning(String label, Date date, Double amount) {
        this.label = label;
        this.date = date;
        this.amount = amount;
    }

    private Earning(Parcel in) {
        id = in.readInt();
        label = in.readString();
        amount = in.readDouble();
        date = new Date(in.readLong());;
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

    public Date getDate() {
        return date;
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
    }

    public static final Parcelable.Creator<Earning> CREATOR = new Parcelable.Creator<Earning>() {
        public Earning createFromParcel(Parcel in) {
            return new Earning(in);
        }

        public Earning[] newArray(int size) {
            return new Earning[size];
        }
    };

    @Override
    public String toString() {
        return "Earning{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
