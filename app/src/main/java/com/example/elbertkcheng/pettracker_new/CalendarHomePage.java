package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.roomorama.caldroid.CaldroidFragment;

import java.io.File;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import hirondelle.date4j.DateTime;


public class CalendarHomePage extends AppCompatActivity {
    private EventRepo dataRepo;

    //Calendar
    private CaldroidFragment caldroidFragment;

    private static final String DATABASE_NAME = "events.db";

    private void setCustomResource(ArrayList<eventBlock> eventList)
    {
        if (caldroidFragment != null)
        {
            ColorDrawable green = new ColorDrawable(Color.GREEN);

            try {
                for (int i = 0; i < dataRepo.getEventList().size(); i++)
                {
                    caldroidFragment.setBackgroundDrawableForDate(green, eventList.get(i).getEventDateTime());
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private EventRepo initializeSampleData(EventRepo db) throws ParseException {

        db.insert(new eventBlock("Grooming", "03/03/2018"));
        db.insert(new eventBlock("Vet", "01/01/2018"));
        db.insert(new eventBlock("Playdate", "05/08/2018"));
        db.insert(new eventBlock("Playdate", "06/9/2018"));
        return db;
    }

    private void createCalendar()
    {
        //Caldroid - flexible calendar - https://github.com/roomorama/Caldroid/blob/master/README.md
        this.caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(caldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefault);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        this.caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, this.caldroidFragment);
        t.commit();
    }

    private boolean databaseCheck(String fileName)
    {
        File dataFile = getApplicationContext().getDatabasePath(fileName);
        if (dataFile.exists())
        {
            Log.i("Database", "Database exists!");
            return true;
        }
        Log.i("Database", "Database DOESN'T exist!");
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        //Create Caldroid calendar
        createCalendar();

        //Custom Dates

        try {
            if (!databaseCheck(DATABASE_NAME))
            {
                this.dataRepo = new EventRepo(this, DATABASE_NAME);
                initializeSampleData(this.dataRepo);
            }
            else
            {
                this.dataRepo = new EventRepo(this, DATABASE_NAME);
            }
            setCustomResource(dataRepo.getEventList());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        caldroidFragment.refreshView();
        //ListView

        ListAdapter mAdapter = null;
        try {
            mAdapter = new myAdapter(this, dataRepo.getEventList());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ListView mListView = findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);

        //Navigation Menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item)
                    {
                        item.setChecked(true);

                        //Actions of the items
                        switch (item.getItemId())
                        {
                            case R.id.nav_add_event:
                                startActivity(new Intent(CalendarHomePage.this, AddEvent.class));
                                Log.v("add event", "event added");
                                return true;
                            case R.id.nav_settings:

                                return true;
                            case R.id.nav_logout:

                                return true;

                            default:
                                return true;
                        }
                    }
                }
        );


    }
}
