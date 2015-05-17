package com.fingers.six.elarm.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhanVanTrung on 2015/05/16.
 */
public class AlarmHandler extends SQLiteOpenHelper {

    private String _tableName;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "alarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_TIME = "TIME";
    private static final String KEY_TIMESTATUS = "TIME_STATUS";


    public  AlarmHandler(Context context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _tableName = tableName;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + _tableName + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TIME + " TEXT NOT NULL,"
                + KEY_TIMESTATUS + " TEXT NOT NULL)";
        db.execSQL(query);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + _tableName);

        // Create tables again
        onCreate(db);
    }

    // Adding new alarm time
    public void addAlarmTime(String time,String timestatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME,time);
        values.put(KEY_TIMESTATUS, timestatus);

        // Inserting Row
        db.insert(_tableName, null, values);
        db.close(); // Closing database connection
    }

    // Getting all Alarm time
    public ArrayList<AlarmTime> getAllAlarmTime() {
        ArrayList<AlarmTime> alarmTimeList = new ArrayList<AlarmTime>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + _tableName + " ORDER BY " + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmTime malarmTime = new AlarmTime();
                malarmTime.setId(Integer.parseInt(cursor.getString(0)));
                malarmTime.setTime(cursor.getString(1));
                malarmTime.setTime_status(cursor.getString(2));
                // Adding word to list
                alarmTimeList.add(malarmTime);
            } while (cursor.moveToNext());
        }

        // return word list
        return alarmTimeList;
    }

    // Updating single word
//    public int updateWord(Word word) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_ENG, word.get_eng());
//        values.put(KEY_JAP, word.get_jap());
//        values.put(KEY_SCORE, 0);
//
//        // updating row
//        return db.update(_tableName, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(word.get_id())});
//    }

    // Deleting single word
//    public void deleteWord(Word word) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(_tableName, KEY_ID + " = ?",
//                new String[]{String.valueOf(word.get_id())});
//        db.close();
//    }
//
//    // Getting words count
//    public int getWordsCount() {
//        String countQuery = "SELECT * FROM " + _tableName;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }


}
