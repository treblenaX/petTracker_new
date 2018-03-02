package com.example.elbertkcheng.pettracker_new;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by elber on 3/1/2018.
 */

  public class SampleEvents {
     private String eventName;
     private SimpleDateFormat eventDate;

     public SampleEvents(String name, SimpleDateFormat date)
     {
         this.eventName = name;
         this.eventDate = date;
     }

     public void setEventDate(SimpleDateFormat eventDate)
     {
         if (this.eventDate == null)
         {
             this.eventDate = eventDate;
         }
     }

     public String getEventDate()
     {
         return eventDate.toPattern();
     }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String toString()
    {
        return eventName + " " + getEventDate();
    }
}
