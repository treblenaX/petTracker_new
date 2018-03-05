package com.example.elbertkcheng.pettracker_new;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elber on 3/3/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    //Version number to upgrade database version - Note: each time you Add, Edit table, you need to change the number.
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "events.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates table
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + eventBlock.TABLE + "("
                + eventBlock.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + eventBlock.KEY_eventName + " TEXT,"
                + eventBlock.KEY_eventDate + " DATE )";
        db.execSQL(CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed but all data will be gone.
        db.execSQL("DROP TABLE IF EXISTS " + eventBlock.TABLE);

        //Recreate tables
        onCreate(db);
    }
}