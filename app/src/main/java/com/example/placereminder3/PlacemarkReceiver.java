package com.example.placereminder3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class PlacemarkReceiver extends BroadcastReceiver {

    private ArrayList<PlacemarkEntry> placemarks;
    public PlacemarkReceiver(ArrayList<PlacemarkEntry> placemarks){
        this.placemarks=placemarks;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String id=intent.getAction();
        if(id.equals("placemark_added")){
            PlacemarkEntry tmp=intent.getParcelableExtra("placemark");
            placemarks.add(0,tmp);
        }
        if(id.equals("placemark_list_updated")){
            ArrayList<PlacemarkEntry>tmp=intent.getParcelableArrayListExtra("placemarks");
            if(tmp==null){
                return;
            }
            placemarks.clear();
            for(int i=0;i<tmp.size();i++){
                placemarks.add(tmp.get(i));
            }
        }

        if(id.equals("placemark_modified")){
            PlacemarkEntry tmp=intent.getParcelableExtra("placemark");
            Log.d("a", "placemarks:"+placemarks);
            int pos=intent.getIntExtra("position",0);
            placemarks.remove(pos);
            placemarks.add(0,tmp);
        }

    }
}