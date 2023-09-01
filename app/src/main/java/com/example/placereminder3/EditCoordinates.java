package com.example.placereminder3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditCoordinates extends Fragment {

    public EditText nameText, latitudeText,longitudeText, descriptionText;
    public PlacemarkEntry entry;

    public EditCoordinates(PlacemarkEntry entry) {
        this.entry=entry;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.coordinates_fragment, container, false);

        nameText=view.findViewById(R.id.nameText);
        latitudeText=view.findViewById(R.id.latitudeText);
        longitudeText=view.findViewById(R.id.longitudeText);
        descriptionText=view.findViewById(R.id.descrText);
        setNameText(entry.getName());
        setLatitudeText(entry.getLatitude());
        setLongitudeText(entry.getLongitude());
        setDescriptionText(entry.getDescription());

        return view;
    }

    public void setNameText(String nameText) {
        this.nameText.setText(nameText);
    }

    public String getNameText() {
        String text=nameText.getText().toString();
        if(text.isEmpty()){
            throw new RuntimeException("Enter a valid name");
        }
        return text;
    }

    public double getLatitudeText() {
        double value;
        String text=latitudeText.getText().toString();
        if(text.isEmpty()){
            throw new RuntimeException("The latitude is not valid");
        }

        value=Double.parseDouble(latitudeText.getText().toString());
        if(value<-180 || value>180){
            throw new RuntimeException("The value in latitude is not in range");
        }

        return value;
    }
    public double getLongitudeText() {
        double value;
        String text=longitudeText.getText().toString();
        if(text.isEmpty()){
            throw new RuntimeException("The longitude is not valid");
        }
        value=Double.parseDouble(longitudeText.getText().toString());
        if(value<-180 || value>180){
            throw new RuntimeException("The value in longitude is not in range");
        }
        return value;
    }
    public String getDescriptionText() {
        return descriptionText.getText().toString();
    }

    public void setLatitudeText(Double latitudeText) {
        this.latitudeText.setText(String.format(latitudeText.toString()));
    }

    public void setLongitudeText(Double longitudeText) {
        this.longitudeText.setText(String.format(longitudeText.toString()));
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText.setText(descriptionText);
    }

}
