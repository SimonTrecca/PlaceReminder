package com.example.placereminder3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddPlacemarkPagerAdapter extends FragmentPagerAdapter {
    private final int ADDRESS=0;
    private final int COORDINATES=1;

    public AddPlacemarkPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case ADDRESS:
                return new AddAddress();

            case COORDINATES:
                return new AddCoordinates();


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
                return "Add with address";

            case COORDINATES:
                return "Add with coordinates";


        }
        return "";
    }
}
