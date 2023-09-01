package com.example.placereminder3;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class EditPlacemark extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> names;
    private PlacemarkEntry entry;
    private int position;
    private Geocoder coder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_placemark);
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()==null){
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coder = new Geocoder(this);

        position=getIntent().getIntExtra("position",0);
        entry= getIntent().getParcelableExtra("currentEntry");
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        EditPlacemarkPagerAdapter adapter = new EditPlacemarkPagerAdapter(getSupportFragmentManager(),entry);
        viewPager.setAdapter(adapter);
        names = getIntent().getStringArrayListExtra("placemarkNames");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addplace_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_code = item.getItemId();
        if (item_code == android.R.id.home) {
            onBackPressed();
        }
        if (item_code == R.id.save_place) {
            PlacemarkEntry tmp = null;
            try{
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);
                if (fragment instanceof EditAddress) {
                    EditAddress frag = (EditAddress) fragment;
                    String address = frag.getAddressText();
                    double lat = 0, lng = 0;
                    try {
                        Iterator<Address> addresses = coder.getFromLocationName(address, 1).iterator();
                        if (addresses != null) {
                            while (addresses.hasNext()) {
                                Address loc = addresses.next();
                                lat = loc.getLatitude();
                                lng = loc.getLongitude();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String inputName=frag.getNameText();
                    for(int i=0;i<names.size();i++){
                        if(names.get(i).equals(inputName) && i!=position){
                            frag.setNameText("");
                            throw new RuntimeException("There is already a placemark with this name");
                        }
                    }
                    Toast.makeText(this, lat + " " + lng, Toast.LENGTH_SHORT).show();
                    tmp = new PlacemarkEntry(lat, lng, frag.getNameText(), frag.getDescriptionText(), frag.getAddressText(), formattedDateTime);
                }
                if (fragment instanceof EditCoordinates) {

                    EditCoordinates frag = (EditCoordinates) fragment;
                    double lat = frag.getLatitudeText();
                    double lng = frag.getLongitudeText();
                    String placeName = "", featureName = "", conSting = "", road = "";
                    try {
                        Iterator<Address> addresses = coder.getFromLocation(lat, lng, 1).iterator();
                        if (addresses != null) {
                            while (addresses.hasNext()) {
                                Address loc = addresses.next();
                                placeName = loc.getLocality();
                                featureName = loc.getFeatureName();
                                conSting = loc.getCountryName();
                                road = loc.getThoroughfare();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String address = road + " " + featureName + " " + placeName + " " + conSting;
                    String inputName=frag.getNameText();
                    for(int i=0;i<names.size();i++){
                        if(names.get(i).equals(inputName) && i!=position){
                            frag.setNameText("");
                            throw new RuntimeException("There is already a placemark with this name");
                        }
                    }
                    tmp = new PlacemarkEntry(lat, lng, frag.getNameText(), frag.getDescriptionText(), address, formattedDateTime);


                }
            }catch(RuntimeException e){
                showAlertDialog(e.getMessage());
                return false;
            }

            Intent intent = new Intent("placemark_modified");
            intent.putExtra("placemark", tmp);
            intent.putExtra("position",position);
            sendBroadcast(intent);
            finish();
        }

        return true;
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Wrong input");
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}