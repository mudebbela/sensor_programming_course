package com.example.hw8contactmanagementapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";

    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, email text not null, phone text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    public DBAdapter(android.content.Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    public long insertContact(String name, String email, String phone) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ContactConstants.CONTACT_NAME, name);
        initialValues.put(ContactConstants.CONTACT_EMAIL, email);
        initialValues.put(ContactConstants.CONTACT_PHONE,phone );
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, ContactConstants.CONTACT_NAME,
                ContactConstants.CONTACT_EMAIL, ContactConstants.CONTACT_PHONE}, null, null, null, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {  db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) { e.printStackTrace(); }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
}



