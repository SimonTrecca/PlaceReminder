package com.example.placereminder3;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import org.osmdroid.util.GeoPoint;

import java.util.List;


public class GeofenceHelper extends ContextWrapper {

    PendingIntent pendingIntent;
    public GeofenceHelper(Context base) {
        super(base);
    }

    public GeofencingRequest getGeofencingRequest(List<Geofence> geofences){

        return new GeofencingRequest.Builder()
                .addGeofences(geofences)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }

    public Geofence getGeofence(String ID, GeoPoint latLng, float radius, int transitionTypes){

        return new Geofence.Builder()
                .setCircularRegion(latLng.getLatitude(),latLng.getLongitude(),radius)
                .setRequestId(ID)
                .setTransitionTypes(transitionTypes)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }


    public PendingIntent getPendingIntent(){

        if(pendingIntent!=null){
            return pendingIntent;
        }
        Intent intent=new Intent(this, GeofenceBroadcastReceiver.class);
        pendingIntent= PendingIntent.getBroadcast(this,2607,intent,PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);

        return pendingIntent;
    }

}
