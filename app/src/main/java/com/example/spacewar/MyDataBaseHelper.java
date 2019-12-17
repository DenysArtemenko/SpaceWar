package com.example.spacewar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = MyDatabaseContract.DB_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_RECORDS =
            "CREATE TABLE " + MyDatabaseContract .TableRecords.TABLE_NAME  + " (" +
                    MyDatabaseContract .TableRecords.COLUMN_NAME_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDatabaseContract .TableRecords.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP +
                    MyDatabaseContract .TableRecords.COLUMN_NAME_TIME + TEXT_TYPE +                " )";

    private static final String SQL_DELETE_RECORDS =
            "DROP TABLE IF EXISTS " + MyDatabaseContract .TableRecords.TABLE_NAME;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);        }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RECORDS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);        }
}
