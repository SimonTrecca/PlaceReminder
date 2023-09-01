package com.example.placereminder3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class AddCoordinates extends Fragment {

    public EditText nameText, latitudeText,longitudeText, descriptionText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.coordinates_fragment, container, false);

        nameText=view.findViewById(R.id.nameText);
        latitudeText=view.findViewById(R.id.latitudeText);
        longitudeText=view.findViewById(R.id.longitudeText);
        descriptionText=view.findViewById(R.id.descrText);

        return view;
    }

    public void setNameText(String text) {
        this.nameText.setText(text);
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

}
