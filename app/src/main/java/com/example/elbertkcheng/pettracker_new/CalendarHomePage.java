package com.example.elbertkcheng.pettracker_new;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;

import java.io.File;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;



public class CalendarHomePage extends AppCompatActivity {
    private EventRepo dataRepo;
    private String user;
    public ListAdapter mAdapter;
    public myAdapter adapter;
    public ListView mListView;
    public SwipeRefreshLayout srl;

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

    private void initializeSampleData(EventRepo db) throws ParseException {

        db.insert(new eventBlock( "Grooming", "03/03/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser()));
        db.insert(new eventBlock( "Vet", "01/01/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser()));
        db.insert(new eventBlock("Playdate", "05/08/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser()));
        db.insert(new eventBlock( "Playdate", "06/9/2018", "1122 228th Avenue SE Sammamish, WA 98075", getUser()));
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void refreshListView(ListView l)
    {
        l.removeAllViewsInLayout();

        //Delays until data is done
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                try {
                    Log.i("New Data", dataRepo.getUserEventList(getUser()).toString());
                    adapter = new myAdapter(getApplicationContext(), dataRepo.getUserEventList(getUser()));
                    adapter.notifyDataSetChanged();
                    mListView.setAdapter(adapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                srl.setRefreshing(false);

                Toast.makeText(getApplicationContext(), "Refreshed List!", Toast.LENGTH_SHORT);
            }
        }, 1000);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        refreshListView(this.mListView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        this.deleteDatabase(DATABASE_NAME);

        setUser((String) getIntent().getSerializableExtra("user"));
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

        //Create Caldroid calendar
        createCalendar();
        caldroidFragment.refreshView();


        //ListView
        try {
            adapter = new myAdapter(getApplicationContext(), dataRepo.getUserEventList(getUser()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mListView = findViewById(R.id.list_view);

        //Makes the button clickable
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                ArrayList<eventBlock> eventList = new ArrayList<>();

                try {
                    eventList = dataRepo.getUserEventList(getUser());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                Log.i("REFRESH", "REFRESHINTERACTED");
                if (mListView != null)
                {
                    refreshListView(mListView);
                }
            }
        });

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
                                Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                                intent.putExtra("user", getUser());
                                startActivity(intent);

                                Log.v("add event", "event added");
                                return true;
                            case R.id.nav_settings:
                                getApplicationContext().deleteDatabase(DATABASE_NAME);
                                adapter.notifyDataSetChanged();
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
