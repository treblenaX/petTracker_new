package com.example.elbertkcheng.pettracker_new;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by elber on 3/2/2018.
 */

public class eventBlock {
    private static final String eventDatePattern = "yyyy-MM-dd";
    private String eventName;
    private String eventDate;
    private Date eventDateObject;


    public eventBlock(String name, String date)
    {
        this.eventName  = name;
        this.eventDate = date;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    //Creates a Date object with the parsed String input.
    public void initializeDate() {
        try {

            this.eventDateObject = new SimpleDateFormat(eventDatePattern).parse(this.eventDate);

        } catch (ParseException e) {

            System.out.println("Can't parse" + this.eventDate);
        }
    }

    public String toString()
    {
        return getEventName() + " " + this.eventDate;
    }
}
