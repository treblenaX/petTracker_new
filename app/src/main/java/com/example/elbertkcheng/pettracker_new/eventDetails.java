package com.example.elbertkcheng.pettracker_new;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class eventDetails extends AppCompatActivity {
    private EventRepo dataRepo;
    TextView displayEventName;
    TextView displayEventTime;
    TextView displayEventDate;
    private String eventAddress;
    private ArrayList<eventBlock> grabbedEvents;
    private int position;
    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getSupportActionBar().setTitle("Event Details");
        this.act = this;
        //Get the object passed through the intent
        setGrabbedEvents((ArrayList) getIntent().getSerializableExtra("object"));
        setPosition((int) getIntent().getSerializableExtra("position"));

        setEventAddress(grabbedEvents.get(position).getAddress());

        //Back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        displayEventName = (TextView) findViewById(R.id.displayEventName);
        displayEventTime = (TextView) findViewById(R.id.displayEventTime);
        displayEventDate = (TextView) findViewById(R.id.displayEventDate);
        Button openInMaps = (Button) findViewById(R.id.openInMap);
        Button updateEvent = (Button) findViewById(R.id.editEventButton);

        displayEventName.setText(grabbedEvents.get(position).getEventName());
        displayEventTime.setText(grabbedEvents.get(position).getStarttime() + " to " + grabbedEvents.get(position).getEndtime());
        displayEventDate.setText(grabbedEvents.get(position).getEventDate());

        openInMaps.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToMap(Uri.parse(createUri(getEventAddress())));
            }
        }));

        updateEvent.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), EditEvent.class);
                intent.putExtra("object", getGrabbedEvents().get(position));
                startActivity(intent);
            }
        }));


    }


    public String createUri(String address)  {
        String uri = "";
        try {
            uri = "geo:0,0?q=" + URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public void goToMap(Uri geo)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geo);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }


    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public ArrayList<eventBlock> getGrabbedEvents() {
        return grabbedEvents;
    }

    public void setGrabbedEvents(ArrayList<eventBlock> grabbedEvents) {
        this.grabbedEvents = grabbedEvents;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EventRepo getDataRepo() {
        return dataRepo;
    }

    public void setDataRepo(EventRepo dataRepo) {
        this.dataRepo = dataRepo;
    }
}
