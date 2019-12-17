package com.example.spacewar;

import android.provider.BaseColumns;

public class MyDatabaseContract {

    public MyDatabaseContract() {
    }
    public static final String DB_NAME = "MyDatabase.db";
    public static abstract class TableRecords implements BaseColumns{
        public static final String TABLE_NAME = "records";
        public static final String COLUMN_NAME_RECORD_ID = "recordid";
        public static final String COLUMN_NAME_NICKNAME = "nickname";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
