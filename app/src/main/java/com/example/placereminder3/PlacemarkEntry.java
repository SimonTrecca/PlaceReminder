package com.example.placereminder3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PlacemarkEntry implements Parcelable {

    private double latitude,longitude;
    private String name,description,address,date;

    public PlacemarkEntry(double latitude, double longitude, String name, String description, String address,String date){

        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        this.description=description;
        this.address=address;
        this.date=date;
    }




    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {

        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final Creator<PlacemarkEntry> CREATOR = new Creator<PlacemarkEntry>() {
        @Override
        public PlacemarkEntry createFromParcel(Parcel in) {
            return new PlacemarkEntry(in);
        }

        @Override
        public PlacemarkEntry[] newArray(int size) {
            return new PlacemarkEntry[size];
        }
    };
    protected PlacemarkEntry(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        description = in.readString();
        address = in.readString();
        date = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeString(date);
    }
}
