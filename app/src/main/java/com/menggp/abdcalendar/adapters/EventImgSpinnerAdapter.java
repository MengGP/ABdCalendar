package com.menggp.abdcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.menggp.abdcalendar.R;

import java.util.List;

/*
    Custom spinner adapter - для выбора изображения
 */
public class EventImgSpinnerAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private int dropdownLayout;
    private List<Integer> imgCollection;

    public EventImgSpinnerAdapter(Context context, int resource, int dropdownResource, List<Integer> imgCollection) {
        super(context, resource, imgCollection);
        this.imgCollection = imgCollection;
        this.layout = resource;
        this.dropdownLayout = dropdownResource;
        this.inflater = LayoutInflater.from(context);
    } // end_constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        ImageView eventImgView = (ImageView)view.findViewById(R.id.event_img_on_spinner_item);
        int eventImgInt = imgCollection.get(position);
        eventImgView.setImageResource( eventImgInt );

        return view;
    } // end_method

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.dropdownLayout, parent, false);

        ImageView eventImgView = (ImageView)view.findViewById(R.id.event_img_on_spinner_dropdown);
        int eventImgInt = imgCollection.get(position);
        eventImgView.setImageResource( eventImgInt );

        return view;
    } // end_method

} // end_class
