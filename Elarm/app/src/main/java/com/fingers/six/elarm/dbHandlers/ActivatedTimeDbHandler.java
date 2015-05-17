package com.fingers.six.elarm.dbHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.fingers.six.elarm.utils.DateTimeUtils;
import org.joda.time.LocalDateTime;

public class ActivatedTimeDbHandler extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "ACTIVATED_TIME";
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "elarm_db";

    // Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_TIME = "TIME";

    public ActivatedTimeDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_TIME + " LONG)";
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

    public int update(LocalDateTime date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(date);

        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + "=?",
                new String[]{"1"});
    }

    public int get() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_TIME}, KEY_ID + " = ?",
                new String[]{"1"}, null, null, null, null);
        cursor.close();
        cursor.moveToFirst();

        return Integer.parseInt(cursor.getString(1));
    }

    private ContentValues getContentValues(LocalDateTime date) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, DateTimeUtils.convertToSeconds(date));
        return values;
    }
}
