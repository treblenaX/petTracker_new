package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarHomePage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<eventBlock> sampledata = new ArrayList();


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void initializeSampleData() {

        sampledata.add(new eventBlock("Grooming","3/10/2018"));
        sampledata.add(new eventBlock("Vet", "3/15/2018"));
        sampledata.add(new eventBlock("Playdate", "3/18/2018"));
        sampledata.add(new eventBlock("Vet", "3/15/2018"));
        sampledata.add(new eventBlock("Playdate", "3/18/2018"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        initializeSampleData();

        //ListView

        ListAdapter mAdapter = new myAdapter(this, sampledata);
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
