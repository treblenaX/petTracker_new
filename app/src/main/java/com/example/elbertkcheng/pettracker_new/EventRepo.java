package com.example.elbertkcheng.pettracker_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class EventRepo {
    /**
     * EventRepo class uses SQLiteDatabase queries to either INSERT, DELETE, UPDATE, or get eventLists
     * either specific to the user or its entirety.
     */

    private DBHelper dbHelper;
    private ArrayList<eventBlock> UserEventList;

    public EventRepo(Context context, String database)
    {
        dbHelper = new DBHelper(context, database);
    }

    public int insert(eventBlock event)
    {
        //Open connection to access the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Using ContentValues to put values into a set.
        values.put(eventBlock.KEY_eventName, event.getEventName());
        values.put(eventBlock.KEY_eventDate, event.getEventDate());
        values.put(eventBlock.KEY_address, event.getAddress());
        values.put(eventBlock.KEY_USER, event.getEventUser());
        values.put(eventBlock.KEY_eventStartTime, event.getStarttime());
        values.put(eventBlock.KEY_eventEndTime, event.getEndtime());

        //Inserts with the set of values fetched from the ContentValues
        long event_id = db.insert(eventBlock.TABLE, null, values);
        db.close(); //Closes db connection
        return (int) event_id;
    }

    public void delete(int event_id)
    {
        //Open connection to access the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Find the event with the id and deletes it.
        db.delete(eventBlock.TABLE, eventBlock.KEY_ID + "= ?", new String[] { String.valueOf(event_id)});
        db.close(); //Closes db connection
    }

    public void update(eventBlock event)
    {
        //Open connection to access the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Using ContentValues to put values into a set.
        values.put(eventBlock.KEY_eventName, event.getEventName());
        values.put(eventBlock.KEY_eventDate, event.getEventDate());
        values.put(eventBlock.KEY_address, event.getAddress());
        values.put(eventBlock.KEY_USER, event.getEventUser());
        values.put(eventBlock.KEY_eventStartTime, event.getStarttime());
        values.put(eventBlock.KEY_eventEndTime, event.getEndtime());

        //Updates the database event values (using the specific eventID) with the new event in the parameter
        db.update(eventBlock.TABLE, values, eventBlock.KEY_ID + "= ?", new String[] {String.valueOf(event.getEventID())});
        db.close(); //Close db connection
    }


    public ArrayList<eventBlock> getEventList() throws ParseException {

        //Open connection to have access to READ the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + eventBlock.TABLE ;
        ArrayList<eventBlock> eventList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        eventBlock event = null;

        //Using the event values grabbed from the entire list from the database, create new eventBlocks and add it to an ArrayList.
        if(cursor.moveToFirst())
        {
            do {
                event = new eventBlock(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                event.setEventID(Integer.parseInt(cursor.getString(0)));

                eventList.add(event);
            } while (cursor.moveToNext());
        }

        //Sorts the list by dates
        Collections.sort(eventList);

        return eventList;
    }

    public void setUserEventList(String username) throws ParseException {
        //Open connection to have access to READ the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<eventBlock> eventList = new ArrayList<>();

        //Only grab the list with the row that has the USER value the same with the parameter
        String query = "SELECT " + eventBlock.KEY_ID + " , " +
                eventBlock.KEY_eventName + ", " +
                eventBlock.KEY_eventDate + ", " +
                eventBlock.KEY_address + ", " +
                eventBlock.KEY_USER + ", " +
                eventBlock.KEY_eventStartTime + ", " +
                eventBlock.KEY_eventEndTime + " FROM "
                + eventBlock.TABLE
                + " WHERE "
                + eventBlock.KEY_USER + " = " + "\"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);

        //new eventBlock created per event and added to an ArrayList
        if(cursor.moveToFirst())
        {
            do {
                eventBlock event = new eventBlock(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                event.setEventID(Integer.parseInt(cursor.getString(0)));

                eventList.add(event);
            } while (cursor.moveToNext());
        }

        //Sorts the list by dates
        Collections.sort(eventList);

        this.UserEventList = eventList;
    }


//    public eventBlock getEventById(int id) throws ParseException {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT " +
//                eventBlock.KEY_ID + " , " +
//                eventBlock.KEY_eventName + ", " +
//                eventBlock.KEY_eventDate + ", " +
//                eventBlock.KEY_address + ", " +
//                eventBlock.KEY_USER + ", " +
//                eventBlock.KEY_eventStartTime + ", " +
//                eventBlock.KEY_eventEndTime +
//                " FROM " + eventBlock.TABLE
//                + " WHERE " +
//                eventBlock.KEY_ID + "= ?";
//        int count = 0;
//        eventBlock event = new eventBlock();
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id)});
//
//        if (cursor.moveToFirst())
//        {
//            do {
//                event.setEventID(cursor.getInt(cursor.getColumnIndex(eventBlock.KEY_ID)));
//                event.setEventName(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventName)));
//                event.setEventDate(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventDate)));
//                event.setAddress(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_address)));
//                event.setEventUser(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_USER)));
//                event.setStarttime(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventStartTime)));
//                event.setEndtime(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventEndTime)));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return event;
//    }

    public ArrayList<eventBlock> getUserEventList() {
        return this.UserEventList;
    }
}
