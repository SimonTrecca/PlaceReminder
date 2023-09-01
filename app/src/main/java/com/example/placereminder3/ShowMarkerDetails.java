package com.example.placereminder3;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ShowMarkerDetails extends AppCompatActivity {

    private PlacemarkEntry entry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        entry=getIntent().getParcelableExtra("placemark");
        TextView nameText,addressText, coordinatesText, descriptionText, dateText;
        nameText=findViewById(R.id.nameText);
        addressText=findViewById(R.id.addressText);
        coordinatesText=findViewById(R.id.coordinatesText);
        descriptionText=findViewById(R.id.descriptionText);
        dateText=findViewById(R.id.dateText);

        nameText.setText(entry.getName());
        addressText.setText(entry.getAddress());
        coordinatesText.setText(entry.getLatitude()+","+entry.getLongitude());
        descriptionText.setText(entry.getDescription());
        dateText.setText(entry.getDate());


        Toolbar toolbar=findViewById(R.id.details_toolBar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()==null){
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.showmarker_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_code= item.getItemId();
        if(item_code==android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}




