package com.example.elbertkcheng.pettracker_new;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elber on 2/12/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static Context context;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME +
            " (" + FeedReaderContract.FeedEntry.COLUMN_LOGIN_NAME + " Login" + FeedReaderContract.FeedEntry.COLUMN_PASSWORD + " Password) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void insert()
    {
        //Creates a map of values where column names are the keys
        ContentValues value = new ContentValues();

    }
    @Override
    public void onCreate(SQLiteDatabase data)
    {
        //Creates SQL Data table
        data.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase data, int oldVersion, int newVersion)
    {
        //Upgrade = discard data and start over
        data.execSQL(SQL_DELETE_ENTRIES);
        onCreate(data);
    }

}
