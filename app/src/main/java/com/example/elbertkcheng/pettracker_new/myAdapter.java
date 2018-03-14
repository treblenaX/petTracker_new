package com.example.elbertkcheng.pettracker_new;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by elber on 3/1/2018.
 */

class myAdapter extends ArrayAdapter<eventBlock> {
    ArrayList<eventBlock> example;

    myAdapter(Context context, ArrayList<eventBlock> example)
    {
        //Context grabbed from the activity - background information
        //Layout is the custom row view template
        //Third parameter is the data
        super(context, R.layout.custom_row_view, example);
        this.example = example;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Inflate = preparation for rendering - grabs info from background
        LayoutInflater listViewInflater = LayoutInflater.from(getContext());
        View mView = listViewInflater.inflate(R.layout.custom_row_view, parent, false);

        eventBlock item = example.get(position);
        TextView tvEventName = (TextView) mView.findViewById(R.id.eventName);
        TextView tvEventDate = (TextView) mView.findViewById(R.id.eventDate);

        tvEventName.setText(item.getEventName());

        tvEventDate.setText(item.getFormattedDate());

        return mView;
    }
}
