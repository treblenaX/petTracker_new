package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

public class CalendarHomePage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home_page);

        //Title of the app as the header
        getSupportActionBar().setTitle("PetTracker");

        //Navigation Drawer Menu stuff
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item)
                    {
                        item.setChecked(true);

                        //close drawer when item it's tapped
                        mDrawerLayout.closeDrawers();


                        //Actions of the items
                        switch (item.getItemId())
                        {
                            case R.id.nav_add_event:

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
