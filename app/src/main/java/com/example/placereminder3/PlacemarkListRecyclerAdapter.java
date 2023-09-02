package com.example.placereminder3;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacemarkListRecyclerAdapter extends RecyclerView.Adapter<PlacemarkListRecyclerAdapter.ViewHolder> {

    private ArrayList<PlacemarkEntry> placemarks;
    private final Activity activity;
    public PlacemarkListRecyclerAdapter(ArrayList<PlacemarkEntry> dataList, Activity activity){
        placemarks=dataList;
        this.activity=activity;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText, addressText, coordinatesText, descriptionText,dateText;
        private Button deleteButton,editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText= itemView.findViewById(R.id.nameText);
            addressText= itemView.findViewById(R.id.addressText);
            coordinatesText= itemView.findViewById(R.id.coordinatesText);
            descriptionText= itemView.findViewById(R.id.descriptionText);
            dateText=itemView.findViewById(R.id.dateText);
            deleteButton=itemView.findViewById(R.id.delete_button);
            editButton=itemView.findViewById(R.id.edit_button);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(placemarks.get(position).getName());
        holder.coordinatesText.setText(placemarks.get(position).getLatitude()+","+placemarks.get(position).getLongitude());
        holder.addressText.setText(placemarks.get(position).getAddress());
        holder.descriptionText.setText(placemarks.get(position).getDescription());
        holder.dateText.setText(placemarks.get(position).getDate());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=holder.getLayoutPosition();
                placemarks.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(0,placemarks.size());
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=holder.getLayoutPosition();
                Intent intent=new Intent(activity,EditPlacemark.class);
                ArrayList<String> names=new ArrayList<String>();
                for(int i=0;i<placemarks.size();i++){
                    names.add(placemarks.get(i).getName());
                }

                intent.putStringArrayListExtra("placemarkNames",names);
                intent.putExtra("currentEntry",placemarks.get(pos));
                intent.putExtra("position",pos);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placemarks.size();
    }



}
