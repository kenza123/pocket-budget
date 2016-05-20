package fr.ig2i.pocketbudget.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ghitakhamaily on 18/04/16.
 */
public class Balance implements Parcelable {

    private int id;
    private Double amount;
    private Date date;
    private Timestamp createdAt;

    public Balance() {
    }

    private Balance(Parcel in) {
        this.id = in.readInt();
        this.amount = in.readDouble();
        this.date = new Date(in.readLong());
        this.createdAt = new Timestamp(in.readLong());
    }

    public int getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeDouble(getAmount());
        parcel.writeLong(getDate().getTime());
        parcel.writeLong(getCreatedAt().getTime());
    }

    public static final Creator<Balance> CREATOR = new Creator<Balance>() {
        public Balance createFromParcel(Parcel in) {
            return new Balance(in);
        }

        public Balance[] newArray(int size) {
            return new Balance[size];
        }
    };

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", createdAt=" + createdAt +
                '}';
    }
}
