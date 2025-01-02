package com.main.mp1.openmiba;

import android.os.Parcel;
import android.os.Parcelable;

public class Gesture implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Gesture createFromParcel(Parcel in) {
            return new Gesture(in);
        }

        public Gesture[] newArray(int size) {
            return new Gesture[size];
        }
    };
    private boolean[] gesture = new boolean[9];
    private int picID;

    public Gesture(int picID2, boolean[] gesture2) {
        this.picID = picID2;
        this.gesture = gesture2;
    }

    public Gesture(Parcel in) {
        this.picID = in.readInt();
        in.readBooleanArray(this.gesture);
    }

    public void writeToParcel(Parcel parcel, int arg1) {
        parcel.writeInt(this.picID);
        parcel.writeBooleanArray(this.gesture);
    }

    public int describeContents() {
        return 0;
    }

    public int getPicID() {
        return this.picID;
    }

    public boolean[] getGesture() {
        return this.gesture;
    }

    public String toString() {
        String res = "(" + this.picID + ":";
        for (boolean z : this.gesture) {
            if (z) {
                res = String.valueOf(res) + 1;
            } else {
                res = String.valueOf(res) + 0;
            }
        }
        return String.valueOf(res) + ")";
    }
}