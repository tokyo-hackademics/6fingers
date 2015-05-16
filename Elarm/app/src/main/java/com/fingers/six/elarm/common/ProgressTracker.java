package com.fingers.six.elarm.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProgressTracker extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "TRACKING_DB";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "elarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_DATE = "DATE";
    private static final String KEY_QID = "QUESTION_LIST_ID";
    private static final String KEY_LIST_NAME = "LIST_NAME";
    private static final String KEY_SCORE = "SCORE";

    public ProgressTracker(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DATE + " INTEGER, "
                + KEY_QID + " INTEGER, "
                + KEY_LIST_NAME + " TEXT NOT NULL, "
                + KEY_SCORE + " INTEGER)";
        db.execSQL(query);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    private long CalcDays(String date) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        Date date1 = myFormat.parse("01 01 2000");
        Date date2 = myFormat.parse(date);
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void add(HistoryItem historyItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(historyItem);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single record
//    public QuestionList getAQuestionList(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
//                        LISTNAME}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        return new QuestionList(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
//    }

    // Getting All question lists
    public List<HistoryItem> getAllHistory() {
        List<HistoryItem> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(getHistoryItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return list;
    }

    public List<HistoryItem> searchByDateAndName(long date, String name) {
        if (name.isEmpty()) {
            return getAllHistory();
        }
        List<HistoryItem> historyItems = new ArrayList<>();
        // Select Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME
                + " WHERE (" + KEY_LIST_NAME + " = '" + name + "' AND " + KEY_DATE + " = " + date + ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                historyItems.add(getHistoryItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return historyItems;
    }

    public int updateHistory(HistoryItem historyItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(historyItem);

        // updating row
        return db.update(TABLE_NAME, values, KEY_DATE + " = ?",
                new String[]{String.valueOf(historyItem.get_date())});
    }

//    // Deleting single List
//    public void deleteList(QuestionList list) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, KEY_ID + " = ?",
//                new String[]{String.valueOf(list.get_id())});
//        db.close();
//    }

//    // Getting words count
//    public int getListsCount() {
//        String countQuery = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//    public int getMaxId() {
//        String maxQuery = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(maxQuery, null);
//
//        // TODO: Check this
//        cursor.close();
//        cursor.moveToFirst();
//
//        // return count
//        return Integer.parseInt(cursor.getString(0));
//    }

    private HistoryItem getHistoryItemFromCursor(Cursor cursor) {
        return new HistoryItem(Long.parseLong(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));
    }

    private ContentValues getContentValues(HistoryItem historyItem) {
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, historyItem.get_date());
        values.put(KEY_QID, historyItem.get_qid());
        values.put(KEY_LIST_NAME, historyItem.get_questionName());
        values.put(KEY_SCORE, historyItem.get_score());
        return values;
    }
}
