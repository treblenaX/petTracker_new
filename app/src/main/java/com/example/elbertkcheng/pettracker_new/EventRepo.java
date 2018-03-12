package com.example.elbertkcheng.pettracker_new;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by elber on 3/3/2018.
 */

public class EventRepo {
    private DBHelper dbHelper;

    public EventRepo(Context context, String database)
    {
        dbHelper = new DBHelper(context, database);
    }

    public int insert(eventBlock event)
    {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(eventBlock.KEY_eventName, event.getEventName());
        values.put(eventBlock.KEY_eventDate, event.getEventDate());

        //Inserts Row
        long event_id = db.insert(eventBlock.TABLE, null, values);
        db.close(); //Closes db connection
        return (int) event_id;
    }

    public void delete(int event_id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(eventBlock.TABLE, eventBlock.KEY_ID + "= ?", new String[] { String.valueOf(event_id)});
        db.close();
    }

    public void update(eventBlock event)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(eventBlock.KEY_eventName, event.getEventName());
        values.put(eventBlock.KEY_eventDate, event.getEventDate());

        db.update(eventBlock.TABLE, values, eventBlock.KEY_ID + "= ?", new String[] {String.valueOf(event.getEventID())});
        db.close(); //Close db connection
    }


    public ArrayList<eventBlock> getEventList() throws ParseException {
        ArrayList<eventBlock> eventList = new ArrayList<>();

        String query = "SELECT * FROM " + eventBlock.TABLE + " ORDER BY " + eventBlock.KEY_ID + " ASC";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        eventBlock event = null;
        if(cursor.moveToFirst())
        {
            do {
                event = new eventBlock(cursor.getString(1), cursor.getString(2));
                event.setEventID(Integer.parseInt(cursor.getString(0)));

                eventList.add(event);
            } while (cursor.moveToNext());
        }

        //Sorts the list by dates
        Collections.sort(eventList);

        return eventList;
    }

    public eventBlock getEventById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                eventBlock.KEY_ID + " , " +
                eventBlock.KEY_eventName + ", " +
                eventBlock.KEY_eventDate +
                " FROM " + eventBlock.TABLE
                + " WHERE " +
                eventBlock.KEY_ID + "= ?";
        int count = 0;
        eventBlock event = new eventBlock();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id)});

        if (cursor.moveToFirst())
        {
            do {

                event.setEventID(cursor.getInt(cursor.getColumnIndex(eventBlock.KEY_ID)));
                event.setEventName(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventName)));
                event.setEventDate(cursor.getString(cursor.getColumnIndex(eventBlock.KEY_eventDate)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return event;
    }
}
