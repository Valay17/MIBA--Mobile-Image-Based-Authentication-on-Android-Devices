package com.main.mp1.openpassgo;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinates implements Parcelable{
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };
    private int x;
    private int y;

    public Coordinates(int x2, int y2) {
        this.x = x2;
        this.y = y2;
    }

    public Coordinates(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
    }

    public void writeToParcel(Parcel parcel, int arg1) {
        parcel.writeInt(this.x);
        parcel.writeInt(this.y);
    }

    public int describeContents() {
        return 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "(" + this.x + "|" + this.y + ")";
    }

    public boolean equals(Object o) {
        if (!o.getClass().equals(Coordinates.class)) {
            return false;
        }
        if (((Coordinates) o).getX() == this.x && ((Coordinates) o).getY() == this.y) {
            return true;
        }
        return false;
    }

}
