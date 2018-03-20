package com.example.elbertkcheng.pettracker_new;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class EditEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String DATABASE_NAME = "events.db";
    private EditText addEventName;
    private EditText addEventAddress;
    private Spinner addEventStartTime;
    private Spinner addEventEndTime;
    private Spinner monthSpinner;
    private Spinner daysSpinner;
    private Spinner yearsSpinner;
    private String username;
    private String eventName;
    private String eventAddress;
    private String startTime;
    private String endTime;
    private String am;
    private String pm;
    private int months;
    private int days;
    private int years;
    private Spinner startTimeAMPM;
    private Spinner endTimeAMPM;
    private EventRepo dataRepo = new EventRepo(this, DATABASE_NAME);
    private eventBlock existing;

    public void updateDatabase(EventRepo repo) throws ParseException {
        Log.i("Check", getEventName().toString() + getEventAddress().toString() + getMonths() + getDays() + getYears() + existing.getEventUser());
        existing.setEventDate(getMonths() + "/" + getDays() + "/" + getYears());
        existing.setEventName(getEventName());
        existing.setAddress(getEventAddress());
        existing.setStarttime(getStartTime() + " " + getAm());
        existing.setEndtime(getEndTime() + " " + getPm());
        repo.update(getExisting());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //Brings the selected eventBlock over
        setExisting((eventBlock)getIntent().getSerializableExtra("object"));

        addEventName = (EditText) findViewById(R.id.addEventName);
        addEventAddress = (EditText) findViewById(R.id.addEventAddress);
        addEventStartTime = (Spinner) findViewById(R.id.startTime);
        addEventEndTime = (Spinner) findViewById(R.id.endTime);
        startTimeAMPM = (Spinner) findViewById(R.id.startTimeAMPM);
        endTimeAMPM = (Spinner) findViewById(R.id.endTimeAMPM);
        monthSpinner = (Spinner) findViewById(R.id.MonthDropDown);
        daysSpinner = (Spinner) findViewById(R.id.DayDropDown);
        yearsSpinner = (Spinner) findViewById(R.id.YearDropDown);
        Button updateEvent = (Button) findViewById(R.id.updateEvent);
        Button deleteEvent = (Button) findViewById(R.id.deleteEvent);

        addEventName.setText(getExisting().getEventName());
        addEventAddress.setText(getExisting().getAddress());

        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> stAdapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> etAdapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> stAdapterAMPM = ArrayAdapter.createFromResource(this, R.array.ampm, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> etAdapterAMPM = ArrayAdapter.createFromResource(this, R.array.ampm, android.R.layout.simple_spinner_dropdown_item);


        Date existingDate = getExisting().getEventDateTime();
        Calendar cal = new GregorianCalendar().getInstance();
        cal.setTime(existingDate);

        monthSpinner.setAdapter(mAdapter);
        if (cal.get(Calendar.MONTH) != 0 )
        {
            int spinnerPosition = mAdapter.getPosition("" + (cal.get(Calendar.MONTH) + 1));
            Log.i("spinner", "" + spinnerPosition);
            monthSpinner.setSelection(spinnerPosition);
        }

        daysSpinner.setAdapter(dAdapter);
        if (cal.get(Calendar.DAY_OF_MONTH) != 0 )
        {
            int spinnerPosition = dAdapter.getPosition("" + cal.get(Calendar.DAY_OF_MONTH));
            Log.i("spinner", "" + spinnerPosition);
            daysSpinner.setSelection(spinnerPosition);
        }

        yearsSpinner.setAdapter(yAdapter);
        if (cal.get(Calendar.YEAR) != 0 )
        {
            int spinnerPosition = yAdapter.getPosition("" + cal.get(Calendar.YEAR));
            Log.i("spinner", "" + spinnerPosition);
            yearsSpinner.setSelection(spinnerPosition);
        }

        Scanner scan = new Scanner(getExisting().getStarttime());
        String existingST = scan.next();
        String existingAMPM = scan.next();
        scan.close();

        Log.i("TEST", existingST);
        Log.i("TEST", existingAMPM);

        Scanner scan2 = new Scanner(getExisting().getEndtime());
        String existingET = scan2.next();
        String existingEtAMPM = scan2.next();
        Log.i("TEST", existingET);
        Log.i("TEST", existingEtAMPM);
        scan2.close();

        addEventStartTime.setAdapter(stAdapter);
        if (existingST != null)
        {
            int spinnerPosition = stAdapter.getPosition(existingST);
            Log.i("spinner", "" + spinnerPosition);
            addEventStartTime.setSelection(spinnerPosition);
        }

        addEventEndTime.setAdapter(etAdapter);
        if (existingET != null)
        {
            int spinnerPosition = stAdapter.getPosition(existingET);
            Log.i("spinner", "" + spinnerPosition);
            addEventEndTime.setSelection(spinnerPosition);
        }

        startTimeAMPM.setAdapter(stAdapterAMPM);
        if (existingAMPM != null)
        {
            int spinnerPosition = stAdapterAMPM.getPosition(existingAMPM);
            Log.i("spinner", "" + spinnerPosition);
            startTimeAMPM.setSelection(spinnerPosition);
        }

        endTimeAMPM.setAdapter(etAdapterAMPM);
        if (existingEtAMPM != null)
        {
            int spinnerPosition = etAdapterAMPM.getPosition(existingEtAMPM);
            Log.i("spinner", "" + spinnerPosition);
            endTimeAMPM.setSelection(spinnerPosition);
        }

        monthSpinner.setOnItemSelectedListener(this);
        daysSpinner.setOnItemSelectedListener(this);
        yearsSpinner.setOnItemSelectedListener(this);
        addEventStartTime.setOnItemSelectedListener(this);
        addEventEndTime.setOnItemSelectedListener(this);
        startTimeAMPM.setOnItemSelectedListener(this);
        endTimeAMPM.setOnItemSelectedListener(this);

        updateEvent.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setEventName(addEventName.getText().toString());
                setEventAddress(addEventAddress.getText().toString());
                setMonths(Integer.parseInt((String)monthSpinner.getSelectedItem()));
                setDays(Integer.parseInt((String)daysSpinner.getSelectedItem()));
                setYears(Integer.parseInt((String)yearsSpinner.getSelectedItem()));
                setStartTime((String) addEventStartTime.getSelectedItem());
                setEndTime((String) addEventEndTime.getSelectedItem());
                setAm((String) startTimeAMPM.getSelectedItem());
                setPm((String) endTimeAMPM.getSelectedItem());

                try {
                    updateDatabase(getDataRepo());
                    eventDetails.act.finish();
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ;
            }
        }));

        deleteEvent.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dataRepo.delete(existing.getEventID());
                eventDetails.act.finish();
                finish();
            }
        }));
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

    public eventBlock getExisting() {
        return existing;
    }

    public void setExisting(eventBlock existing) {
        this.existing = existing;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }
}
