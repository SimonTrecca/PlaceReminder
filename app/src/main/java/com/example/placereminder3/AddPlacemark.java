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

public class AddPlacemark extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> names;
    private Geocoder coder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.save_placemark);
        Toolbar toolbar=findViewById(R.id.add_toolBar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()==null){
           return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        coder=new Geocoder(this);

        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        AddPlacemarkPagerAdapter adapter = new AddPlacemarkPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        names=getIntent().getStringArrayListExtra("placemarkNames");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.addplace_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_code= item.getItemId();
        if(item_code==android.R.id.home){
            onBackPressed();
        }
        if(item_code==R.id.save_place){
            PlacemarkEntry tmp=null;
            try {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);

                if (fragment instanceof AddAddress) {

                    AddAddress frag = (AddAddress) fragment;
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

                    Toast.makeText(this, lat + " " + lng, Toast.LENGTH_SHORT).show();
                    if (names.contains(frag.getNameText())) {
                        frag.setNameText("");
                        throw new RuntimeException("There is already a placemark with this name");
                    }
                    tmp = new PlacemarkEntry(lat, lng, frag.getNameText(), frag.getDescriptionText(), frag.getAddressText(), formattedDateTime);
                }

                if (fragment instanceof AddCoordinates) {

                    AddCoordinates frag = (AddCoordinates) fragment;
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

                    if (names.contains(frag.getNameText())) {
                        frag.setNameText("");
                        throw new RuntimeException("There is already a placemark with this name");
                    }
                    tmp = new PlacemarkEntry(lat, lng, frag.getNameText(), frag.getDescriptionText(), address, formattedDateTime);

                }
            }catch(RuntimeException e){
                showAlertDialog(e.getMessage());
                return false;
            }

            Intent intent=new Intent("placemark_added");
            intent.putExtra("placemark",tmp);
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




