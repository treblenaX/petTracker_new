package com.example.elbertkcheng.pettracker_new;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by elber on 3/2/2018.
 */

public class eventBlock implements Comparable<eventBlock>{
    private static final String eventDatePatternInput = "MM/dd/yyyy";
    private static final String eventDatePatternOutput = "EEE, d MMM 'at' hh:mm aa";
    private int eventID;
    private String eventName;
    private String eventDate;
    private Date eventDateObject;

    //Labels table name
    public static final String TABLE = "Events";

    //Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_eventName = "name";
    public static final String KEY_eventDate = "date";

    public eventBlock() throws ParseException {
    }

    public eventBlock(String eventName, String eventDate) throws ParseException {
        this.eventName = eventName;
        this.eventDate = eventDate;

        SimpleDateFormat sdf = new SimpleDateFormat(eventDatePatternInput);
        this.eventDateObject = sdf.parse(eventDate);
        Log.i("Date", this.eventDateObject.toString());
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getEventDate()
    {
        return this.eventDate;
    }

    public void setEventDate(String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(eventDatePatternOutput);

        return getEventName() + " " + sdf.format(this.eventDateObject);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public Date getEventDateTime()
    {
        return this.eventDateObject;
    }

    @Override
    public int compareTo(eventBlock eb) {
        if (getEventDateTime() == null || eb.getEventDateTime() == null)
        {
            return 0;
        }
        return getEventDateTime().compareTo(eb.getEventDateTime());
    }
}
