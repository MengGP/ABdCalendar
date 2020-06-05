package com.menggp.abdcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.menggp.abdcalendar.R;

import java.util.List;

/*
    Custom spinner adapter - для строковых значений
 */
public class StrSpinnerAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;
    private int layout;
    private int dropdownLayout;
    private List<String> strList;

    public StrSpinnerAdapter(Context context, int resource, int dropdownResource, List<String> strList) {
        super(context, resource, strList);
        this.strList = strList;
        this.layout = resource;
        this.dropdownLayout = dropdownResource;
        this.inflater = LayoutInflater.from(context);
    } // end_constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView textView = (TextView) view.findViewById(R.id.str_spinner_text1);
        String strItem = strList.get(position);
        textView.setText( strItem );

        return view;
    } // end_method

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.dropdownLayout, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.str_spinner_text1);
        String strItem = strList.get(position);
        textView.setText(strItem);

        return view;
    } // end_method

} // end_class
