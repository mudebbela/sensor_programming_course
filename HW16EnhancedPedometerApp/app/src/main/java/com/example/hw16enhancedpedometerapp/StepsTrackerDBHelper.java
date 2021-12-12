package com.example.hw16enhancedpedometerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

public class StepsTrackerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StepsTrackerDatabase";
    private static final String TABLE_STEPS_SUMMARY = "StepsTrackerSummary";
    private static final String ID = "id";
    private static final String STEP_TYPE = "steptype";
    private static final String STEP_TIME = "steptime"; //time is in milliseconds Epoch Time
    private static final String STEP_DATE = "stepdate"; //Date format is mm/dd/yyyy
    private static final String CREATE_TABLE_STEPS_SUMMARY = "CREATE TABLE " +
            TABLE_STEPS_SUMMARY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            STEP_DATE + " TEXT,"+ STEP_TIME + " INTEGER,"+ STEP_TYPE + " TEXT"+")";

    private static final int DATABASE_VERSION = 1;
    private static final int RUNNING = 3;
    private static final int JOGGING = 2;
    private static final int WALKING = 1;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STEPS_SUMMARY);
//        this.onCreate(db);
    }

    public StepsTrackerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STEPS_SUMMARY);

    }

    public boolean createStepsEntry(long timeStamp, int stepType)
    {
        boolean createSuccessful = false;
        Calendar mCalendar = Calendar.getInstance();
        String todayDate = String.valueOf(mCalendar.get(Calendar.MONTH)+1)+"/" +
                String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH))+"/" +
                String.valueOf(mCalendar.get(Calendar.YEAR));
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STEP_TIME, timeStamp);
            values.put(STEP_DATE, todayDate);
            values.put(STEP_TYPE, stepType);
            long row = db.insert(TABLE_STEPS_SUMMARY, null, values);
            if(row!=-1) { createSuccessful = true; }
            db.close();
        } catch (Exception e) { e.printStackTrace(); }
        return createSuccessful;
    }

    public int [] getStepsByDate(String date)
    {
        int stepType[] = new int[3];
        String selectQuery = "SELECT " + STEP_TYPE + " FROM " + TABLE_STEPS_SUMMARY +" WHERE " +
                STEP_DATE +" = '"+date + "'";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    switch(c.getInt((c.getColumnIndex(STEP_TYPE))))
                    {
                        case WALKING: ++stepType[0];
                            break;
                        case JOGGING: ++stepType[1];
                            break;
                        case RUNNING: ++stepType[2];
                            break;
                    }
                } while (c.moveToNext());
            }
            db.close();
        } catch (Exception e) { e.printStackTrace(); }
        return stepType;
    }
}