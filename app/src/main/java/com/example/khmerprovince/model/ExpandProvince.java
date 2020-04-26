package com.example.khmerprovince.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ExpandProvince implements Parcelable {

    public final String name;

    public ExpandProvince(String name) {
        this.name = name;
    }

    protected ExpandProvince(Parcel in) {
        name = in.readString();
    }

    public static final Creator<ExpandProvince> CREATOR = new Creator<ExpandProvince>() {
        @Override
        public ExpandProvince createFromParcel(Parcel in) {
            return new ExpandProvince(in);
        }

        @Override
        public ExpandProvince[] newArray(int size) {
            return new ExpandProvince[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
