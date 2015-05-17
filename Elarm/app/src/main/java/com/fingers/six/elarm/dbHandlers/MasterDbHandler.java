package com.fingers.six.elarm.dbHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fingers.six.elarm.common.Data;
import com.fingers.six.elarm.common.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class MasterDbHandler extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "MASTER_DB";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "elarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_LIST_NAME = "KEY_LIST_NAME";

    public MasterDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ContentValues values = getContentValues("KANJI_N5");

        // Inserting Row
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
        getWritableDatabase().insert(TABLE_NAME, null, values);

        values = getContentValues("Toefl");
        getWritableDatabase().insert(TABLE_NAME, null, values);

        values = getContentValues("Toeic");
        getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_LIST_NAME + " TEXT NOT NULL)";
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

        ContentValues values = getContentValues(listName);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        // new table
        //(new WordListDbHandler())
        db.close();

    }

    // Getting single record
    public QuestionList getAQuestionList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_LIST_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        cursor.moveToFirst();

//        cursor.close();

        return getQuestionListFromCursor(cursor);
    }

    // Getting All question lists
    public List<QuestionList> getAllQuestionList() {
        List<QuestionList> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_LIST_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(getQuestionListFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return list;
    }

    public List<QuestionList> search(String keyword) {
        if (keyword.isEmpty()) {
            return getAllQuestionList();
        }
        List<QuestionList> questionPackList = new ArrayList<>();
        // Select Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME
                + " WHERE (" + KEY_LIST_NAME + " LIKE '%" + keyword + "%') ORDER BY " + KEY_LIST_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding word to list
                questionPackList.add(getQuestionListFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return questionPackList;
    }

    // Updating single list name
    public int updateListName(QuestionList qlist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(qlist.get_name());

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(qlist.get_id())});
    }

    // Deleting single List
    public void deleteList(QuestionList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(list.get_id())});
    }

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

    private ContentValues getContentValues(String listName) {
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_NAME, listName);
        return values;
    }

    private QuestionList getQuestionListFromCursor(Cursor cursor) {
        return new QuestionList(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
    }
}
