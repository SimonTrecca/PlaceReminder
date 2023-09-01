package com.example.placereminder3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditAddress extends Fragment {

    public EditText nameText, addressText,descriptionText;
    public PlacemarkEntry entry;

    public EditAddress(PlacemarkEntry entry) {
        this.entry=entry;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.address_fragment, container, false);
        nameText=view.findViewById(R.id.nameText);
        addressText=view.findViewById(R.id.addressText);
        descriptionText=view.findViewById(R.id.descrText);
        setNameText(entry.getName());
        setAddressText(entry.getAddress());
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

    public void setAddressText(String addressText) {
        this.addressText.setText(addressText);
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText.setText(descriptionText);
    }
}
