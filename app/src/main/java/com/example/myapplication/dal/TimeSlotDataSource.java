package com.example.myapplication.dal;

import static com.example.myapplication.dal.DatabaseHelper.COLUMN_BARBER_ID;
import static com.example.myapplication.dal.DatabaseHelper.COLUMN_DATE_TABLE;
import static com.example.myapplication.dal.DatabaseHelper.COLUMN_STATUS_TABLE;
import static com.example.myapplication.dal.DatabaseHelper.COLUMN_TIME_SLOT_ID;
import static com.example.myapplication.dal.DatabaseHelper.COLUMN_TIME_START_TABLE;
import static com.example.myapplication.dal.DatabaseHelper.TIME_SLOT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.myapplication.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_TIME_SLOT_ID,
            COLUMN_TIME_START_TABLE,
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

    public static ArrayList<TimeSlot> selectAllTimeSlot(Context context) {
        ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        TimeSlotDataSource timeSlotDataSource = new TimeSlotDataSource(context);
        timeSlotDataSource.open();
        Cursor cursor = db.query(DatabaseHelper.TIME_SLOT_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(cursor.getInt(0));
                timeSlot.setTimeStart(cursor.getString(1));
                timeSlot.setStatus(cursor.getString(2));
                timeSlot.setDate(cursor.getString(3));
                timeSlot.setIdBarber(cursor.getInt(4));
                timeSlots.add(timeSlot);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return timeSlots;
    }

    public TimeSlot addTimeSlot(TimeSlot timeSlot) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_START_TABLE, timeSlot.getTimeStart());
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

    public List<TimeSlot> insertTimeSlotForDate(String date, int barberId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "09:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "10:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "10:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "11:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "11:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "12:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "12:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "13:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "13:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "14:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "14:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "15:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "15:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "16:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "16:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "17:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "17:30");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, date);
        values.put(COLUMN_BARBER_ID, barberId);
        db.insert(TIME_SLOT_TABLE, null, values);

        List<TimeSlot> list=getTimeSlotByBarberIdAndDate(barberId,date);
        return list;

    }

    public List<TimeSlot> getTimeSlotByBarberIdAndDate(int barberId, String date) {
        db = dbHelper.getReadableDatabase();
        List<TimeSlot>list=new ArrayList<>();
        String[] columns = {COLUMN_TIME_SLOT_ID,COLUMN_TIME_START_TABLE,COLUMN_STATUS_TABLE, COLUMN_DATE_TABLE, COLUMN_BARBER_ID};
        String selection = COLUMN_BARBER_ID + " = ? AND " + COLUMN_DATE_TABLE + " = ?";
        String[] selectionArgs = {String.valueOf(barberId), date};
        Cursor cursor = db.query(TIME_SLOT_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            if(cursor.moveToNext()){
                do {
                    list.add(cursorToTimeSlot(cursor));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return list;
        }
        return null;
    }

//    public List<TimeSlot> getTimeSlotByBarberIdAndDate(View.OnClickListener context, int barberId, String date) {
//        List<TimeSlot> timeSlots = new ArrayList<>();
//        db = dbHelper.getReadableDatabase();
//        String[] columns = {COLUMN_BARBER_ID, COLUMN_DATE_TABLE};
//        String selection = COLUMN_BARBER_ID + " = ? AND " + COLUMN_DATE_TABLE + " = ?";
//        String[] selectionArgs = {String.valueOf(barberId), date};
//        Cursor cursor = db.query(TIME_SLOT_TABLE, columns, selection, selectionArgs, null, null, null);
//
//    }

    public TimeSlot getTimeSlotById(int id) {
        db = dbHelper.getReadableDatabase();
        String[] columns = {COLUMN_TIME_SLOT_ID, COLUMN_TIME_START_TABLE, COLUMN_STATUS_TABLE, COLUMN_DATE_TABLE, COLUMN_BARBER_ID};
        String selection = COLUMN_TIME_SLOT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TIME_SLOT_TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            TimeSlot timeSlot = cursorToTimeSlot(cursor);
            cursor.close();
            return timeSlot;
        }
        return null;
    }

    public void updateStatusTimeSlot(int id, String status) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_TABLE, status);
        String selection = COLUMN_TIME_SLOT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TIME_SLOT_TABLE, values, selection, selectionArgs);
    }

    public void chooseTimeSlot(int id) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_TABLE, "Booked");
        String selection = COLUMN_TIME_SLOT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TIME_SLOT_TABLE, values, selection, selectionArgs);
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
