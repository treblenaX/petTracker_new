package com.example.elbertkcheng.pettracker_new;

import android.provider.BaseColumns;

/**
 * Created by elber on 2/12/2018.
 */

public class FeedReaderContract {
    private FeedReaderContract() {}
    public static class FeedEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "loginData";
        public static final String COLUMN_LOGIN_NAME = "loginName";
        public static final String COLUMN_PASSWORD = "password";
    }


}
