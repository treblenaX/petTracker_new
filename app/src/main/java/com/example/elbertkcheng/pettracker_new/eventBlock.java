package com.example.elbertkcheng.pettracker_new;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by elber on 3/2/2018.
 */

public class eventBlock implements Comparable<eventBlock>, Serializable {
    private static final String eventDatePatternInput = "MM/dd/yyyy HH:mm aa 'until' HH:mm aa";
    private static final String eventDatePatternOutput = "MMMM d, EEE";
    private int eventID;
    private String eventUser;
    private String eventName;
    private String eventDate;
    private String address;
    private String starttime;
    private String endtime;
    private Date eventDateObject;

    //Labels table name
    public static final String TABLE = "Events";

    //Labels Table Columns names
    public static final String KEY_USER = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_eventName = "name";
    public static final String KEY_eventDate = "date";
    public static final String KEY_eventStartTime = "starttime";
    public static final String KEY_eventEndTime = "endtime";
    public static final String KEY_address = "address";

    public eventBlock() throws ParseException {
    }

    public eventBlock(String eventName, String eventDate, String address, String user, String starttime, String endtime) throws ParseException {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.address = address;
        this.eventUser = user;
        this.starttime = starttime;
        this.endtime = endtime;

        if (this.eventDateObject == null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(eventDatePatternInput);
            this.eventDateObject = sdf.parse(eventDate + " " + getStarttime() + " until " + getEndtime());
            Log.i("Date", this.toString());
        }


    }


    public String getFormattedDate()
    {
        return this.eventDateObject.toString();
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

        return sdf.format(this.eventDateObject) + " at " + getStarttime() + " until " + getEndtime();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEventUser() {
        return eventUser;
    }

    public void setEventUser(String eventUser) {
        this.eventUser = eventUser;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
