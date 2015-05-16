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
public class DbHandler extends SQLiteOpenHelper {

    private String _tableName;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "elarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_ENG = "ENG";
    private static final String KEY_JAP = "JAP";
    private static final String KEY_SCORE = "SCORE";

    public DbHandler(Context context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _tableName = tableName;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + _tableName + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ENG + " TEXT NOT NULL,"
                + KEY_JAP + " TEXT NOT NULL, " + KEY_SCORE + " INTEGER)";
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

    // Adding new word
    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENG, word.get_eng());
        values.put(KEY_JAP, word.get_jap());
        values.put(KEY_SCORE, 0);

        // Inserting Row
        db.insert(_tableName, null, values);
        db.close(); // Closing database connection
    }

    // Getting single word
    public Word getWord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(_tableName, new String[]{KEY_ID,
                        KEY_ENG, KEY_JAP, KEY_SCORE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Word word = new Word(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));

        return word;
    }

    // Getting All Words
    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<Word>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + _tableName + " ORDER BY " + KEY_ENG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.set_id(Integer.parseInt(cursor.getString(0)));
                word.set_eng(cursor.getString(1));
                word.set_jap(cursor.getString(2));
                // Adding word to list
                wordList.add(word);
            } while (cursor.moveToNext());
        }

        // return word list
        return wordList;
    }

    public List<Word> search(String keyword) {
        List<Word> wordList = new ArrayList<Word>();
        // Select Query
        String selectQuery = "SELECT  * FROM " + _tableName + " ORDER BY " + KEY_ENG
                + "WHERE (" + KEY_ENG + " LIKE '%" + keyword + "%'"
                + "OR " + KEY_JAP + " LIKE '%" + keyword + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.set_id(Integer.parseInt(cursor.getString(0)));
                word.set_eng(cursor.getString(1));
                word.set_jap(cursor.getString(2));
                // Adding word to list
                wordList.add(word);
            } while (cursor.moveToNext());
        }

        // return word list
        return wordList;
    }

    // Updating single word
    public int updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENG, word.get_eng());
        values.put(KEY_JAP, word.get_jap());
        values.put(KEY_SCORE, 0);

        // updating row
        return db.update(_tableName, values, KEY_ID + " = ?",
                new String[]{String.valueOf(word.get_id())});
    }

    // Deleting single word
    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(_tableName, KEY_ID + " = ?",
                new String[]{String.valueOf(word.get_id())});
        db.close();
    }

    // Getting words count
    public int getWordsCount() {
        String countQuery = "SELECT * FROM " + _tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int getMaxId() {
        String maxQuery = "SELECT MAX(" + KEY_ID + ") FROM " + _tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(maxQuery, null);

        // TODO: Check this
        cursor.close();
        cursor.moveToFirst();

        // return count
        return Integer.parseInt(cursor.getString(0));
    }
}
