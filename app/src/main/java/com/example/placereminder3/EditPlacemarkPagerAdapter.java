package com.example.placereminder3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EditPlacemarkPagerAdapter extends FragmentPagerAdapter {
    private final int ADDRESS=0;
    private final int COORDINATES=1;
    private final PlacemarkEntry entry;

    public EditPlacemarkPagerAdapter(@NonNull FragmentManager fm,PlacemarkEntry entry) {
        super(fm);
        this.entry=entry;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case ADDRESS:
                return new EditAddress(entry);

            case COORDINATES:
                return new EditCoordinates(entry);


        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case ADDRESS:
                return "Edit from address";

            case COORDINATES:
                return "Edit from coordinates";


        }
        return "";
    }
}
