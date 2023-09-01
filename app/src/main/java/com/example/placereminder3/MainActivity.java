package com.example.placereminder3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.Manifest;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView mappa;
    private IMapController mappaController;
    private ArrayList<PlacemarkEntry> placemarks;
    private PlacemarkReceiver receiver;
    private ManageGeofencesReceiver manageGeofencesReceiver;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Marker currentLocation;
    private final int LOCATION_CODE=0;
    private final int BACKGROUND_CODE=1;
    private final int NOTIFICATION_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placemarks=new ArrayList<PlacemarkEntry>();
        receiver=new PlacemarkReceiver(placemarks);
        manageGeofencesReceiver=new ManageGeofencesReceiver(this);

        IntentFilter intentFilter=new IntentFilter("placemark_added");
        intentFilter.addAction("placemark_list_updated");
        registerReceiver(receiver,intentFilter);
        IntentFilter filter2=new IntentFilter("placemark_added");
        filter2.addAction("placemark_list_updated");
        filter2.addAction("placemark_modified");
        registerReceiver(manageGeofencesReceiver,filter2);

        Toolbar toolbar=findViewById(R.id.main_toolBar);
        setSupportActionBar(toolbar);
        Configuration.getInstance().load(getApplicationContext(),getSharedPreferences("osmdroid",MODE_PRIVATE));
        mappa=findViewById(R.id.map);
        mappaController=mappa.getController();
        mappa.setTileSource(TileSourceFactory.MAPNIK);
        mappaController.setZoom(20.0);

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        Drawable image= ContextCompat.getDrawable(this,R.drawable.you_logo);
        Drawable you=resizeDrawable(image,150,150);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                GeoPoint currentPoint=new GeoPoint(location.getLatitude(),location.getLongitude());
                if(currentLocation==null){
                    currentLocation=new Marker(mappa);
                    currentLocation.setIcon(you);
                    currentLocation.setTitle("You");
                    mappa.getOverlays().add(currentLocation);
                    mappaController.setCenter(currentPoint);
                }
                currentLocation.setPosition(currentPoint);
                mappa.invalidate();


            }
        };

        managePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                accessCurrentLocation();

            }
        }
        if(requestCode==BACKGROUND_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                requestNotificationPermission();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Overlay> overlays=mappa.getOverlays();
        for(Overlay overlay:overlays){
           if(overlay==currentLocation){
               continue;
           }
            mappa.getOverlayManager().remove(overlay);
        }

        for(PlacemarkEntry entry:placemarks){
            Marker marker=new Marker(mappa);
            marker.setTitle(entry.getName());
            marker.setSnippet(entry.getAddress()+" "+entry.getLatitude()+","+entry.getLongitude());
            marker.setPosition(new GeoPoint(entry.getLatitude(),entry.getLongitude()));
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    Intent intent=new Intent(MainActivity.this,ShowMarkerDetails.class);
                    intent.putExtra("placemark",entry);
                    startActivity(intent);
                    return false;
                }
            });
            mappa.getOverlays().add(marker);
        }
        mappa.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_code= item.getItemId();
        if(item_code==R.id.add_place){
            Intent intent=new Intent(MainActivity.this, AddPlacemark.class);
            ArrayList<String> names=new ArrayList<String>();
            for(int i=0;i<placemarks.size();i++){
                names.add(placemarks.get(i).getName());
            }
            intent.putStringArrayListExtra("placemarkNames",names);
            startActivity(intent);
        }
        if(item_code==R.id.see_placelist){
            Intent intent=new Intent(MainActivity.this, PlacemarkList.class);
            intent.putParcelableArrayListExtra("placemarks",placemarks);
            startActivity(intent);
        }
        if(item_code==R.id.go_to_place){
            View anchor = findViewById(R.id.go_to_menu_anchor);
            goToMenu(anchor);
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        Menu menu= popupMenu.getMenu();

        for(int i=0;i<placemarks.size();i++){
            PlacemarkEntry elem=placemarks.get(i);
            menu.add(Menu.NONE,i,i,elem.getName());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int posizione=item.getItemId();
                PlacemarkEntry placemark= placemarks.get(posizione);
                double lat=placemark.getLatitude();
                double lng=placemark.getLongitude();
                mappaController.setCenter(new GeoPoint(lat,lng));

                return true;
            }
        });

        popupMenu.show();
    }

    private void managePermissions(){
        String permission=Manifest.permission.ACCESS_FINE_LOCATION;
        boolean hasPermission=ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED;
        if(hasPermission){
            accessCurrentLocation();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{permission},LOCATION_CODE);
        }

    }

    private void accessCurrentLocation(){
        String provider=null;
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            provider=LocationManager.NETWORK_PROVIDER;
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            provider=LocationManager.GPS_PROVIDER;
            requestBackgroundPermission();
        }
        if(provider==null){
            return;
        }
        locationManager.requestLocationUpdates(provider,0,0,locationListener);
    }

    public void requestBackgroundPermission(){
        String permission=Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        boolean hasPermission=ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED;
        if(!hasPermission){
            ActivityCompat.requestPermissions(this,new String[]{permission},BACKGROUND_CODE);
        }
        else{
            requestNotificationPermission();
        }
    }
    public void requestNotificationPermission(){

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU) {
            return;
        }
        String permission = Manifest.permission.POST_NOTIFICATIONS;
        boolean hasPermission=ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED;
        if(!hasPermission){
            ActivityCompat.requestPermissions(this,new String[]{permission},NOTIFICATION_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(manageGeofencesReceiver);
    }

    public Drawable resizeDrawable(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            return new BitmapDrawable(getResources(), resizedBitmap);
        } else {
            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return new BitmapDrawable(getResources(), newBitmap);
        }
    }

}