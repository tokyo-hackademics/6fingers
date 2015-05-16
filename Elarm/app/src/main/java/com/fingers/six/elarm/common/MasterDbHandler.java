package com.fingers.six.elarm.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nghia on 5/16/2015.
 */
public class MasterDbHandler extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "MASTER_DB";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "elarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String LISTNAME = "LISTNAME";

    public MasterDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + LISTNAME + " TEXT NOT NULL)";
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

    // Adding new questionList
    public void addList(String listName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LISTNAME, listName);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single record
    public QuestionList getAQuestionList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        LISTNAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new QuestionList(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
    }

    // Getting All question lists
    public List<QuestionList> getAllQuestionList() {
        List<QuestionList> list = new ArrayList<QuestionList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + LISTNAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(new QuestionList(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        // return word list
        return list;
    }

    public List<QuestionList> search(String keyword) {
        List<QuestionList> questionPackList = new ArrayList<QuestionList>();
        // Select Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + LISTNAME
                + "WHERE (" + LISTNAME + " LIKE '%" + keyword + "%')";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding word to list
                questionPackList.add(new QuestionList(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        // return word list
        return questionPackList;
    }

    // Updating single list name
    public int updateListName(QuestionList qlist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LISTNAME, qlist.get_name());

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(qlist.get_id())});
    }

    // Deleting single List
    public void deleteList(QuestionList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(list.get_id())});
        db.close();
    }

    // Getting words count
    public int getListsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int getMaxId() {
        String maxQuery = "SELECT MAX(" + KEY_ID + ") FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(maxQuery, null);

        // TODO: Check this
        cursor.close();
        cursor.moveToFirst();

        // return count
        return Integer.parseInt(cursor.getString(0));
    }
}
