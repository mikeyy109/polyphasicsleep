package com.liquications.polyphasicsleep.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mike Clarke on 19/08/2014.
 *
 * Not currently used in app.
 */


public class SleepNowDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SleepNowDB";

    public SleepNowDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE sleeps ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "num INT, "+
                "time INT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS sleeps");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_SLEEPS = "sleeps";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SLEEP = "num";
    private static final String KEY_TIME = "time";

    private static final String[] COLUMNS = {KEY_ID,KEY_SLEEP,KEY_TIME};

    public void addSleep(Sleep sleep){
        Log.d("addSleep", sleep.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SLEEP, sleep.getNum()); // get title
        values.put(KEY_TIME, sleep.getTime()); // get author

        // 3. insert
        db.insert(TABLE_SLEEPS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Sleep getSleep(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_SLEEPS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Sleep sleep = new Sleep();
        sleep.setId(Integer.parseInt(cursor.getString(0)));
        sleep.setNum(cursor.getInt(1));
        sleep.setTime(cursor.getString(2));

        Log.d("getSleep("+id+")", sleep.toString());

        // 5. return book
        return sleep;
    }

    // Get All Books
    public List<Sleep> getAllSleeps() {
        List<Sleep> sleeps = new LinkedList<Sleep>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SLEEPS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Sleep sleep = null;
        if (cursor.moveToFirst()) {
            do {
                sleep = new Sleep();
                sleep.setId(Integer.parseInt(cursor.getString(0)));
                sleep.setNum(cursor.getInt(1));
                sleep.setTime(cursor.getString(2));

                // Add book to books
                sleep.add(sleep);
            } while (cursor.moveToNext());
        }

        Log.d("getAllSleeps()", sleeps.toString());

        // return books
        return sleeps;
    }

    // Updating single book
    public int updateSleep(Sleep sleep) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", sleep.getNum()); // get title
        values.put("author", sleep.getTime()); // get author

        // 3. updating row
        int i = db.update(TABLE_SLEEPS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(sleep.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteBook(Sleep sleep) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_SLEEPS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(sleep.getId()) });

        // 3. close
        db.close();

        Log.d("deleteBook", sleep.toString());

    }
}