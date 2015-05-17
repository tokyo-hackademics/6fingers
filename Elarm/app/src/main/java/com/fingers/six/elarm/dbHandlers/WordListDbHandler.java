package com.fingers.six.elarm.dbHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fingers.six.elarm.common.Data;
import com.fingers.six.elarm.common.Word;
import com.fingers.six.elarm.utils.DateTimeUtils;

import org.joda.time.LocalDateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WordListDbHandler extends SQLiteOpenHelper {

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
    private static final String KEY_LAST_CORRECT = "LAST_CORRECT";

    public WordListDbHandler(Context context, String tableName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _tableName = "T_" + tableName.hashCode() + "";

        String query = "CREATE TABLE '" + _tableName + "'("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ENG + " TEXT NOT NULL, "
                + KEY_JAP + " TEXT NOT NULL, " + KEY_SCORE + " INTEGER, "
                + KEY_LAST_CORRECT + " LONG)";
        try {
            getWritableDatabase().execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateSampleData();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE '" + _tableName + "'("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ENG + " TEXT NOT NULL, "
                + KEY_JAP + " TEXT NOT NULL, " + KEY_SCORE + " INTEGER, "
                + KEY_LAST_CORRECT + " LONG)";
        db.execSQL(query);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS '" + _tableName + "'");

        // Create tables again
        onCreate(db);
    }

    private void generateSampleData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '" + _tableName + "'");

        // Create tables again
        onCreate(db);
        for (String str : Data.questionData) {
            Word w = new Word();
            w.set_jap(str.substring(0, 1));
            for (int i = 1; i < str.length(); ++i) {
                if (Character.toLowerCase(str.charAt(i)) <= 'z' && Character.toLowerCase(str.charAt(i)) >= 'a') {
                    w.set_eng(str.substring(i));
                    break;
                }
            }
            w.set_score(0);
            w.set_lastAsked(DateTimeUtils.convertToSeconds(LocalDateTime.now()));

            addWord(w);
        }
    }

    // Adding new word
    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(word);

        // Inserting Row
        db.insert("'" + _tableName + "'", null, values);
    }

    // Getting single word
    public Word getWord(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(_tableName, new String[]{KEY_ID,
                        KEY_ENG, KEY_JAP, KEY_SCORE, KEY_LAST_CORRECT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return getWordfromCursor(cursor);
    }

    // Getting All Words
    public List<Word> getAllWords() throws ParseException {
        List<Word> wordList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM '" + _tableName + "' ORDER BY " + KEY_ENG;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wordList.add(getWordfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return wordList;
    }

    public List<Word> search(String keyword) throws ParseException {
        if (keyword.isEmpty()) {
            return getAllWords();
        }

        List<Word> wordList = new ArrayList<>();


        // Select Query
        String selectQuery = "SELECT  * FROM '" + _tableName + "' ORDER BY " + KEY_ENG
                + "WHERE (" + KEY_ENG + " LIKE '%" + keyword + "%'"
                + "OR " + KEY_JAP + " LIKE '%" + keyword + "%')";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                wordList.add(getWordfromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return word list
        return wordList;
    }

    // Updating single word
    public int updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(word);

        // updating row
        return db.update(_tableName, values, KEY_ID + " = ?",
                new String[]{String.valueOf(word.get_id())});
    }

    // Deleting single word
    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("'" + _tableName + "'", KEY_ID + "= " + word.get_id(),
                null);
    }

//    public int getWordsCount() {
//        String countQuery = "SELECT * FROM " + _tableName;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//    public int getMaxId() {
//        String maxQuery = "SELECT MAX(" + KEY_ID + ") FROM " + _tableName;
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

    private ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(KEY_ENG, word.get_eng());
        values.put(KEY_JAP, word.get_jap());
        values.put(KEY_SCORE, word.get_score());
        values.put(KEY_LAST_CORRECT, word.get_lastAsked());
        return values;
    }

    private Word getWordfromCursor(Cursor cursor) throws ParseException {
        return new Word(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));
    }
}
