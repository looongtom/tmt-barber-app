package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Booking;

import java.util.ArrayList;

public class BookingDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_BOOKING_ID,
            DatabaseHelper.COLUMN_BOOKING_USER_ID,
            DatabaseHelper.COLUMN_BARBER_ID,
            DatabaseHelper.COLUMN_BOOKING_DATE,
            DatabaseHelper.COLUMN_BOOKING_CREATE_TIME,
            DatabaseHelper.COLUMN_BOOKING_SLOT_ID,
            DatabaseHelper.COLUMN_BOOKING_TOTAL,
            DatabaseHelper.COLUMN_BOOKING_STATUS,
            DatabaseHelper.COLUMN_BOOKING_RESULT_ID
    };

    public BookingDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public Booking getById(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.BOOKING_TABLE, allColumns, DatabaseHelper.COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            Booking booking = cursorToBooking(cursor);
            return booking;
        }
        return null;
    }

    public static ArrayList<Booking> getBookingByUserId(Context context, int userId) {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        BookingDataSource bookingDataSource = new BookingDataSource(context);
        bookingDataSource.open();
        String selection = DatabaseHelper.COLUMN_BOOKING_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        // order by booking date desc
        Cursor cursor = db.query(DatabaseHelper.BOOKING_TABLE, allColumns, selection, selectionArgs, null, null, DatabaseHelper.COLUMN_BOOKING_DATE + " DESC");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(0));
                booking.setUserId(cursor.getInt(1));
                booking.setBarberId(cursor.getInt(2));
                booking.setTime(cursor.getString(3));
                booking.setCreateTime(cursor.getString(4));
                booking.setSlotId(cursor.getInt(5));
                booking.setPrice(cursor.getDouble(6));
                booking.setStatus(cursor.getString(7));
                booking.setResultId(cursor.getInt(8));
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookings;
    }

    public static ArrayList<Booking> getBookingByStaffId(Context context, int userId) {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        BookingDataSource bookingDataSource = new BookingDataSource(context);
        bookingDataSource.open();
        String selection = DatabaseHelper.COLUMN_BOOKING_BARBER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        // order by booking date desc
        Cursor cursor = db.query(DatabaseHelper.BOOKING_TABLE, allColumns, selection, selectionArgs, null, null, DatabaseHelper.COLUMN_BOOKING_DATE + " DESC");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(0));
                booking.setUserId(cursor.getInt(1));
                booking.setBarberId(cursor.getInt(2));
                booking.setTime(cursor.getString(3));
                booking.setCreateTime(cursor.getString(4));
                booking.setSlotId(cursor.getInt(5));
                booking.setPrice(cursor.getDouble(6));
                booking.setStatus(cursor.getString(7));
                booking.setResultId(cursor.getInt(8));
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookings;

    }


        public Booking insertBooking(Context context, Booking booking) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_USER_ID, booking.getUserId());
        values.put(DatabaseHelper.COLUMN_BARBER_ID, booking.getBarberId());
        values.put(DatabaseHelper.COLUMN_BOOKING_DATE, booking.getTime());
        values.put(DatabaseHelper.COLUMN_BOOKING_CREATE_TIME, booking.getCreateTime());
        values.put(DatabaseHelper.COLUMN_BOOKING_SLOT_ID, booking.getSlotId());
        values.put(DatabaseHelper.COLUMN_BOOKING_TOTAL, booking.getPrice());
        values.put(DatabaseHelper.COLUMN_BOOKING_STATUS, booking.getStatus());
        long insertId = db.insert(DatabaseHelper.BOOKING_TABLE, null, values);
        Cursor cursor = db.query(DatabaseHelper.BOOKING_TABLE, allColumns, DatabaseHelper.COLUMN_BOOKING_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Booking newBooking = cursorToBooking(cursor);
        cursor.close();
        return newBooking;

    }

    public void updateBookingStatus(int bookingId, String status) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_STATUS, status);
        db.update(DatabaseHelper.BOOKING_TABLE, values, DatabaseHelper.COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
    }

    public void updateBookingPrice(int bookingId, double price) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_TOTAL, price);
        db.update(DatabaseHelper.BOOKING_TABLE, values, DatabaseHelper.COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
    }

    public void updateBooking(Booking booking){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_USER_ID, booking.getUserId());
        values.put(DatabaseHelper.COLUMN_BOOKING_BARBER_ID, booking.getBarberId());
        values.put(DatabaseHelper.COLUMN_BOOKING_DATE, booking.getTime());
        values.put(DatabaseHelper.COLUMN_BOOKING_CREATE_TIME, booking.getCreateTime());
        values.put(DatabaseHelper.COLUMN_BOOKING_SLOT_ID, booking.getSlotId());
        values.put(DatabaseHelper.COLUMN_BOOKING_TOTAL, booking.getPrice());
        values.put(DatabaseHelper.COLUMN_BOOKING_STATUS, booking.getStatus());
        if(booking.getResultId() != null) values.put(DatabaseHelper.COLUMN_BOOKING_RESULT_ID, booking.getResultId());
        db.update(DatabaseHelper.BOOKING_TABLE, values, DatabaseHelper.COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(booking.getId())});
    }

    private Booking cursorToBooking(Cursor cursor) {
        Booking booking = new Booking();
        booking.setId(cursor.getInt(0));
        booking.setUserId(cursor.getInt(1));
        booking.setBarberId(cursor.getInt(2));
        booking.setTime(cursor.getString(3));
        booking.setCreateTime(cursor.getString(4));
        booking.setSlotId(cursor.getInt(5));
        booking.setPrice(cursor.getDouble(6));
        booking.setStatus(cursor.getString(7));
        booking.setResultId(cursor.getInt(8));
        return booking;
    }
}
