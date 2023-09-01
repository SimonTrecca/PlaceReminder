package com.example.placereminder3;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ManageGeofencesReceiver extends BroadcastReceiver {

    private GeofenceHelper geofenceHelper;
    private GeofencingClient geofencingClient;
    private ArrayList<Geofence> geofences;

    public ManageGeofencesReceiver(Context context) {
        geofenceHelper = new GeofenceHelper(context);
        geofencingClient = LocationServices.getGeofencingClient(context);
        geofences=new ArrayList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String id = intent.getAction();
        boolean valid=false;
        if (id.equals("placemark_added")) {
            PlacemarkEntry tmp = intent.getParcelableExtra("placemark");
            addGeofence(tmp);
            Log.d("Geofence Tag", tmp.getAddress());
            valid=true;
        }
        if(id.equals("placemark_modified")){
            PlacemarkEntry tmp=intent.getParcelableExtra("placemark");
            int pos=intent.getIntExtra("position",0);
            editGeofence(pos,tmp);
            valid=true;
        }
        if(id.equals("placemark_list_updated")){
            ArrayList<PlacemarkEntry>tmp=intent.getParcelableArrayListExtra("placemarks");
            if(tmp==null){
                return;
            }
            geofences.clear();
            for(PlacemarkEntry entry:tmp){
                addGeofence(entry);
            }
            valid=true;
        }
        if(valid){
            if(geofences.size()==0){
                return;
            }
            GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofences);
            PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Response addGeofences", "Success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Response addGeofences", "Failure ");
                        }
                    });
        }




    }

    private void addGeofence(PlacemarkEntry entry){
        geofences.add(0,geofenceHelper.getGeofence(entry.getName(),new GeoPoint(entry.getLatitude(),entry.getLongitude()),500,Geofence.GEOFENCE_TRANSITION_ENTER));
    }
    private void removeGeofence(int position){
        geofences.remove(position);
    }
    private void editGeofence(int position,PlacemarkEntry entry){
        removeGeofence(position);
        addGeofence(entry);
    }
}