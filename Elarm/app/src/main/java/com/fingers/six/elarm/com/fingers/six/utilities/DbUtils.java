package com.fingers.six.elarm.com.fingers.six.utilities;

import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by Nghia on 5/16/2015.
 */
public class DbUtils {
    private static SQLiteDatabase _db = SQLiteDatabase.openOrCreateDatabase("ELARM_DB", null);

    public static void addNewQuestionList(String name) {
        String query = "CREATE TABLE IF NOT EXISTS " + name
                + "(" + "ID integer primary key autoincrement, "
                + "ENGLISH text not null, "
                + "JAPANESE text not null, "
                + "SCORE integer)";
        _db.execSQL(query);
    }


}
