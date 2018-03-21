package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;

public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        /**
         * AddEvent class that uses the EventRepo object to insert the newly created event into
         * the database. The new event is created from the values inputted through the text fields and the
         * Spinners (drop-down menus).
         */

    private static final String DATABASE_NAME = "events.db";
    private EditText addEventName;
    private EditText addEventAddress;
    private Spinner addEventStartTime;
    private Spinner addEventEndTime;
    private String username;
    private String eventName;
    private String eventAddress;
    private String startTime;
    private String endTime;
    private String startTimeAMPM;
    private String endTimeAMPM;
    private int months;
    private int days;
    private int years;
    private EventRepo dataRepo = new EventRepo(this, DATABASE_NAME);

    public void addIntoDatabase(EventRepo repo) throws ParseException {
        //Using the getters and the EventRepo object, it inserts the new eventBlock
        repo.insert(new eventBlock(getEventName().toString(), getMonths() + "/" + getDays() + "/" + getYears(), getEventAddress().toString(), getUsername(), getStartTime() + " " + getStartTimeAMPM(), getEndTime() + " " + getEndTimeAMPM()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setTitle("Add Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //The username is set to ensure that the class knows who's logged in.
        setUsername((String) getIntent().getSerializableExtra("user"));
        Log.i("USER IS", getUsername());
        Intent intent = new Intent(getApplicationContext(), eventDetails.class);
        intent.putExtra("user", getUsername());

        addEventName = (EditText) findViewById(R.id.addEventName);
        addEventAddress = (EditText) findViewById(R.id.addEventAddress);
        addEventStartTime = (Spinner) findViewById(R.id.startTime);
        addEventEndTime = (Spinner) findViewById(R.id.endTime);
        Spinner stAMPM = (Spinner) findViewById(R.id.startTimeAMPM);
        Spinner etAMPM = (Spinner) findViewById(R.id.endTimeAMPM);
        Spinner monthSpinner = (Spinner) findViewById(R.id.MonthDropDown);
        Spinner daysSpinner = (Spinner) findViewById(R.id.DayDropDown);
        Spinner yearsSpinner = (Spinner) findViewById(R.id.YearDropDown);
        Button addEvent = (Button) findViewById(R.id.updateEvent);

        addEvent.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setEventName(addEventName.getText().toString());
                setEventAddress(addEventAddress.getText().toString());

                try {
                    addIntoDatabase(getDataRepo());
                    Toast.makeText(getApplicationContext(), "Event successfully added!", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                finish();
            }
        }));

        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> stAdapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> etAdapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> stAdapterAMPM = ArrayAdapter.createFromResource(this, R.array.ampm, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> etAdapterAMPM = ArrayAdapter.createFromResource(this, R.array.ampm, android.R.layout.simple_spinner_dropdown_item);


        monthSpinner.setAdapter(mAdapter);
        daysSpinner.setAdapter(dAdapter);
        yearsSpinner.setAdapter(yAdapter);
        addEventStartTime.setAdapter(stAdapter);
        addEventEndTime.setAdapter(etAdapter);
        stAMPM.setAdapter(stAdapterAMPM);
        etAMPM.setAdapter(etAdapterAMPM);

        monthSpinner.setOnItemSelectedListener(this);
        daysSpinner.setOnItemSelectedListener(this);
        yearsSpinner.setOnItemSelectedListener(this);
        addEventStartTime.setOnItemSelectedListener(this);
        addEventEndTime.setOnItemSelectedListener(this);
        stAMPM.setOnItemSelectedListener(this);
        etAMPM.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.MonthDropDown)
        {
            setMonths(Integer.parseInt((String)adapterView.getItemAtPosition(i)));
        }
        if (adapterView.getId() == R.id.DayDropDown)
        {
            setDays(Integer.parseInt((String)adapterView.getItemAtPosition(i)));
        }
        if (adapterView.getId() == R.id.YearDropDown)
        {
            setYears(Integer.parseInt((String)adapterView.getItemAtPosition(i)));
        }
        if (adapterView.getId() == R.id.startTime)
        {
            setStartTime((String)adapterView.getItemAtPosition(i));
        }
        if (adapterView.getId() == R.id.endTime)
        {
            setEndTime((String)adapterView.getItemAtPosition(i));
        }
        if (adapterView.getId() == R.id.startTimeAMPM)
        {
            setStartTimeAMPM((String)adapterView.getItemAtPosition(i));
        }
        if (adapterView.getId() == R.id.endTimeAMPM)
        {
            setEndTimeAMPM((String)adapterView.getItemAtPosition(i));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            Intent intent = new Intent(getApplicationContext(), CalendarHomePage.class);
            intent.putExtra("user", getUsername());
            finish();
            return true;
        }
        return false;
    }


    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public EventRepo getDataRepo() {
        return dataRepo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTimeAMPM() {
        return startTimeAMPM;
    }

    public void setStartTimeAMPM(String startTimeAMPM) {
        this.startTimeAMPM = startTimeAMPM;
    }

    public String getEndTimeAMPM() {
        return endTimeAMPM;
    }

    public void setEndTimeAMPM(String endTimeAMPM) {
        this.endTimeAMPM = endTimeAMPM;
    }
}
