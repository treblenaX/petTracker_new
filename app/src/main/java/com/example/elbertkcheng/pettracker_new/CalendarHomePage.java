package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.roomorama.caldroid.CaldroidFragment;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarHomePage extends AppCompatActivity {
    /**
     * The CalendarHomePage class is the main class of the application that contains the CalendarView(Caldroid)
     * and the ListView which displays the eventList that is grabbed from the EventRepo class.
     */
    private CaldroidFragment caldroidFragment;
    private DrawerLayout mDrawer;
    private EventRepo dataRepo;
    private String user;
    public myAdapter adapter;
    public ListView mListView;
    public SwipeRefreshLayout srl;
    public ArrayList<eventBlock> eventList;

    private static final String DATABASE_NAME = "events.db";

    private void setCustomResource(ArrayList<eventBlock> eventList)
    {
        //Color the eventdates
        if (caldroidFragment != null)
        {
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            for (int i = 0; i < eventList.size(); i++)
            {
                caldroidFragment.setBackgroundDrawableForDate(green, eventList.get(i).getEventDateTime());
            }
        }

        //Refresh the CaldroidView
        caldroidFragment.refreshView();
    }

    private void clearCalendar(ArrayList<eventBlock> eventList)
    {
        //Clears the calendar of all its colored dates with events from the eventList.
        if (caldroidFragment != null)
        {
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            for (int i = 0; i < eventList.size(); i++)
            {
                caldroidFragment.clearBackgroundDrawableForDate(eventList.get(i).getEventDateTime());
            }
        }
        caldroidFragment.refreshView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void initializeSampleData(EventRepo db) throws ParseException {
        //Sample Data that will be included if the database is non-existent or empty.
        db.insert(new eventBlock( "SAMPLE:Grooming", "03/03/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser(), "7:00 AM", "9:00 AM"));
        db.insert(new eventBlock( "SAMPLE:Vet", "01/01/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser(), "7:00 AM", "9:00 AM"));
        db.insert(new eventBlock("SAMPLE:Playdate", "05/08/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser(), "7:00 AM", "9:00 AM"));
        db.insert(new eventBlock( "SAMPLE:Playdate", "06/9/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser(), "7:00 AM", "9:00 AM"));
    }

    private void createCalendar()
    {
        //Caldroid - flexible calendar - https://github.com/roomorama/Caldroid/blob/master/README.md

        //Start building the CaldroidFragment object and putting arguments in.
        this.caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(caldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefault);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        this.caldroidFragment.setArguments(args);

        //Replace the default CalendarView with the CaldroidFragment
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, this.caldroidFragment);
        t.commit();
    }

    private boolean databaseCheck(String fileName) throws IOException, ParseException {
        //Variable that finds the file specified in parameters
        File databaseFile = getApplicationContext().getDatabasePath(fileName);

        //Uses File.io to find the Database for checking
        File dataFile = databaseFile;

        //Checks if the file is empty
        if (dataFile.exists())
        {
            EventRepo dataCheck = new EventRepo(this, DATABASE_NAME);
            dataCheck.setUserEventList(getUser());

            //Grabs the ArrayList of eventBlock specific to the user logged in and check if it is empty or not.
            if (dataCheck.getUserEventList().isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        //Else, return false to create the sample database.
        return false;
    }

    private void updateData()
    {
        //Updates the dataRepo with a re-grab of the user-specified event list.
        try {
            this.dataRepo.setUserEventList(getUser());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.eventList = this.dataRepo.getUserEventList();

    }

    private void refreshListView(ListView l)
    {
        //Refreshes the ListView by removing the views then creating it again
        l.removeAllViewsInLayout();

        //Delays until data is done loading.
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                updateData();
                adapter = new myAdapter(getApplicationContext(), eventList);
                adapter.notifyDataSetChanged();
                mListView.setAdapter(adapter);
                setCustomResource(eventList);
                srl.setRefreshing(false);
            }
        }, 500);
        Toast.makeText(getApplicationContext(), "Refreshed List!", Toast.LENGTH_SHORT);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateData();
        refreshListView(mListView);
        caldroidFragment.moveToDate(Calendar.getInstance().getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        getSupportActionBar().setTitle("Pet Tracker");
        setUser((String) getIntent().getSerializableExtra("user"));
        //Custom Dates
        try {
            if (!databaseCheck(DATABASE_NAME))
            {
                this.dataRepo = new EventRepo(this, DATABASE_NAME);
                initializeSampleData(this.dataRepo);
                dataRepo.setUserEventList(getUser());
                this.eventList = dataRepo.getUserEventList();
            }
            else
            {
                this.dataRepo = new EventRepo(this, DATABASE_NAME);
                dataRepo.setUserEventList(getUser());
                this.eventList = dataRepo.getUserEventList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create Caldroid calendar
        createCalendar();
        caldroidFragment.refreshView();


        //Set ListView adapter
        adapter = new myAdapter(getApplicationContext(), eventList);
        setCustomResource(eventList);

        mListView = findViewById(R.id.list_view);

        //Makes the button clickable
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), eventDetails.class);
                intent.putExtra("position", position);
                intent.putExtra("id", id);
                intent.putExtra("object", eventList);
                startActivity(intent);
            }
        });


        mListView.setAdapter(adapter);

        srl = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (mListView != null)
                {
                    refreshListView(mListView);
                    try {
                        setCustomResource(dataRepo.getEventList());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Navigation Menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
                                Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                                intent.putExtra("user", getUser());
                                mDrawer.closeDrawers();
                                startActivity(intent);

                                return true;
                            case R.id.nav_deletedata:

                                dataRepo = new EventRepo(getApplicationContext(), DATABASE_NAME);
                                for (int i = 0; i < eventList.size(); i++)
                                {
                                    dataRepo.delete(eventList.get(i).getEventID());
                                }

                                Toast.makeText(getApplicationContext(), "User data completely deleted!", Toast.LENGTH_SHORT).show();

                                clearCalendar(eventList);
                                adapter.notifyDataSetChanged();
                                refreshListView(mListView);
                                caldroidFragment.refreshView();
                                mDrawer.closeDrawers();
                                return true;
                            case R.id.nav_logout:
                                Intent tent = new Intent(getApplicationContext(), LogIn.class);
                                Toast.makeText(getApplicationContext(), "Log-out successful!", Toast.LENGTH_SHORT).show();
                                startActivity(tent);
                                finish();
                                return true;

                            default:
                                return true;
                        }
                    }
                }
        );
    }
}


