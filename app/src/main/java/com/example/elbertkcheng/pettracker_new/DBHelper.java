package com.example.elbertkcheng.pettracker_new;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    /**
     *  Creates an SQLiteDatabase with rawQueries and sets columns with the keys. Upgrades when columns are
     *  added or deleted.
     */

    //Version number to upgrade database version - Note: each time you Add, Edit table, you need to change the number.
    private static final int DATABASE_VERSION = 7;


    public DBHelper(Context context, String database)
    {
        super(context, database, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates table
        String CREATE_TABLE = "CREATE TABLE " + eventBlock.TABLE + "("
                + eventBlock.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + eventBlock.KEY_eventName + " TEXT,"
                + eventBlock.KEY_eventDate + " DATE,"
                + eventBlock.KEY_address + " TEXT,"
                + eventBlock.KEY_USER + " TEXT, "
                + eventBlock.KEY_eventStartTime + " TIME,"
                + eventBlock.KEY_eventEndTime + " TIME )";
        db.execSQL(CREATE_TABLE);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed but all data will be gone.
        db.execSQL("DROP TABLE IF EXISTS " + eventBlock.TABLE);

        //Recreate tables
        onCreate(db);
    }
}
