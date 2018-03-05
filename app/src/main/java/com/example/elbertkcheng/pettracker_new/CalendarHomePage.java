package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CalendarHomePage extends AppCompatActivity {
    private Connection conn;
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

    private void initializeSampleData(EventRepo db) throws ParseException {

        db.insert(new eventBlock("Vet", "01/01/2018"));
        db.insert(new eventBlock("Grooming", "03/03/2018"));
        db.insert(new eventBlock("Playdate", "05/08/2018"));

        ArrayList<eventBlock> list = db.getEventList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        EventRepo repo = new EventRepo(this);
        try {
            this.deleteDatabase("events.db");
            initializeSampleData(repo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //ListView

        ListAdapter mAdapter = null;
        try {
            mAdapter = new myAdapter(this, repo.getEventList());
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
