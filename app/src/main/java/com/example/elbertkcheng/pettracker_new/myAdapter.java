package com.example.elbertkcheng.pettracker_new;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        String singleItemLocation = example.get(position).toString();
        TextView tvEventName = (TextView) mView.findViewById(R.id.eventName);
        TextView tvEventDate = (TextView) mView.findViewById(R.id.eventDate);

        Scanner scan = new Scanner(singleItemLocation);

        tvEventName.setText(scan.next());
        tvEventDate.setText(scan.next());
        return mView;
    }





}
