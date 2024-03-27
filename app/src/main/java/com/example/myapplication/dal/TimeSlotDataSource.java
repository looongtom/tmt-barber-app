package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.TimeSlot;

import java.util.ArrayList;

public class TimeSlotDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_TIME_SLOT_ID,
            DatabaseHelper.COLUMN_TIME_START_TABLE,
            DatabaseHelper.COLUMN_STATUS_TABLE,
            DatabaseHelper.COLUMN_DATE_TABLE,
            DatabaseHelper.COLUMN_BARBER_ID
    };
    public TimeSlotDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public static ArrayList<TimeSlot> selectAllTimeSlot(Context context){
        ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(context);
        timeSlotDataSource.open();
        Cursor cursor = db.query(DatabaseHelper.TIME_SLOT_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            do{
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(cursor.getInt(0));
                timeSlot.setTimeStart(cursor.getString(1));
                timeSlot.setStatus(cursor.getString(2));
                timeSlot.setDate(cursor.getString(3));
                timeSlot.setIdBarber(cursor.getInt(4));
                timeSlots.add(timeSlot);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return timeSlots;
    }

    public TimeSlot addTimeSlot(TimeSlot timeSlot){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIME_START_TABLE, timeSlot.getTimeStart());
        values.put(DatabaseHelper.COLUMN_STATUS_TABLE, timeSlot.getStatus());
        values.put(DatabaseHelper.COLUMN_DATE_TABLE, timeSlot.getDate());
        values.put(DatabaseHelper.COLUMN_BARBER_ID, timeSlot.getIdBarber());
        long insertId = db.insert(DatabaseHelper.TIME_SLOT_TABLE, null, values);
        Cursor cursor = db.query(DatabaseHelper.TIME_SLOT_TABLE, allColumns, DatabaseHelper.COLUMN_TIME_SLOT_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        TimeSlot newTimeSlot = cursorToTimeSlot(cursor);
        cursor.close();
        return newTimeSlot;
    }

    public TimeSlot cursorToTimeSlot(Cursor cursor) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(cursor.getInt(0));
        timeSlot.setTimeStart(cursor.getString(1));
        timeSlot.setStatus(cursor.getString(2));
        timeSlot.setDate(cursor.getString(3));
        timeSlot.setIdBarber(cursor.getInt(4));
        return timeSlot;
    }
}
