package com.example.elbertkcheng.pettracker_new;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class myAdapter extends ArrayAdapter<eventBlock> {
    /**
     * Adapter class that will be used to help display the events in the ListView in the format of
     * custom_row_view.xml
     */

    ArrayList<eventBlock> example;

    myAdapter(Context context, ArrayList<eventBlock> example)
    {
        //Context grabbed from the activity - background information
        //Layout is the custom row view template
        super(context, R.layout.custom_row_view, example);
        this.example = example;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Inflate = preparation for rendering - grabs info from background
        LayoutInflater listViewInflater = LayoutInflater.from(getContext());
        View mView = listViewInflater.inflate(R.layout.custom_row_view, parent, false);

        //Set the correct event information for display for each event
        eventBlock item = example.get(position);
        TextView tvEventName = (TextView) mView.findViewById(R.id.eventName);
        TextView tvEventDate = (TextView) mView.findViewById(R.id.eventDate);

        tvEventName.setText(item.getEventName());
        tvEventDate.setText(item.toString());

        return mView;
    }
}
