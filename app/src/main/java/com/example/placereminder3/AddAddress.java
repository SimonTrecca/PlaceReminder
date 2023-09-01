package com.example.placereminder3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class AddAddress extends Fragment {

    public EditText nameText, addressText,descriptionText;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.address_fragment, container, false);
        nameText=view.findViewById(R.id.nameText);
        addressText=view.findViewById(R.id.addressText);
        descriptionText=view.findViewById(R.id.descrText);


        return view;
    }
    public void setNameText(String text){
        nameText.setText(text);
    }
    public String getNameText() {
        String text=nameText.getText().toString();
        if(text.isEmpty()){
            throw new RuntimeException("Enter a valid name");
        }
        return text;
    }

    public String getAddressText() {
        String text=addressText.getText().toString();
        if(text.isEmpty()){
            throw new RuntimeException("Enter a valid address");
        }
        return text;
    }
    public String getDescriptionText() {
        return descriptionText.getText().toString();
    }
}
