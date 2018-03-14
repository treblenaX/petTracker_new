package com.example.elbertkcheng.pettracker_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;

public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String DATABASE_NAME = "events.db";
    private EditText addEventName;
    private EditText addEventAddress;
    private String username;
    private String eventName;
    private String eventAddress;
    private int months;
    private int days;
    private int years;
    private EventRepo dataRepo = new EventRepo(this, DATABASE_NAME);

    public void addIntoDatabase(EventRepo repo) throws ParseException {
        Log.i("Check", getEventName().toString() + getEventAddress().toString() + getMonths() + getDays() + getYears() + getUsername());
        repo.insert(new eventBlock(getEventName().toString(), getMonths() + "/" + getDays() + "/" + getYears(), getEventAddress().toString(), getUsername()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        setUsername((String) getIntent().getSerializableExtra("user"));
        Log.i("USER IS", getUsername());

        addEventName = (EditText) findViewById(R.id.addEventName);
        addEventAddress = (EditText) findViewById(R.id.addEventAddress);
        Spinner monthSpinner = (Spinner) findViewById(R.id.MonthDropDown);
        Spinner daysSpinner = (Spinner) findViewById(R.id.DayDropDown);
        Spinner yearsSpinner = (Spinner) findViewById(R.id.YearDropDown);
        Button addEvent = (Button) findViewById(R.id.AddEvent);

        addEvent.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setEventName(addEventName.getText().toString());
                setEventAddress(addEventAddress.getText().toString());

                try {
                    addIntoDatabase(getDataRepo());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                finish();
            }
        }));

        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(mAdapter);
        daysSpinner.setAdapter(dAdapter);
        yearsSpinner.setAdapter(yAdapter);

        monthSpinner.setOnItemSelectedListener(this);
        daysSpinner.setOnItemSelectedListener(this);
        yearsSpinner.setOnItemSelectedListener(this);
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void setDataRepo(EventRepo dataRepo) {
        this.dataRepo = dataRepo;
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
}
