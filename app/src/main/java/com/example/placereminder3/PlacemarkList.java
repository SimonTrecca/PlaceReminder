package com.example.placereminder3;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacemarkList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlacemarkListRecyclerAdapter adapter;
    private ArrayList<PlacemarkEntry> placemarks;
    private PlacemarkReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.placemark_list);
        Toolbar toolbar=findViewById(R.id.add_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        placemarks=getIntent().getParcelableArrayListExtra("placemarks");

        receiver=new PlacemarkReceiver(placemarks);
        IntentFilter intentFilter=new IntentFilter("placemark_modified");
        registerReceiver(receiver,intentFilter);

        recyclerView = findViewById(R.id.recyclerView);
        adapter= new PlacemarkListRecyclerAdapter(placemarks,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_code= item.getItemId();
        if(item_code==android.R.id.home){
            Intent intent=new Intent("placemark_list_updated");
            intent.putParcelableArrayListExtra("placemarks",placemarks);
            sendBroadcast(intent);
            finish();
            onBackPressed();
        }

        return true;
    }




}
