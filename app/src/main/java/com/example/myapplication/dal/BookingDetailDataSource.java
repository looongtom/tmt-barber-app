package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.BookingDetail;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_BOOKING_DETAIL_ID,
            DatabaseHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID,
            DatabaseHelper.COLUMN_BOOKING_DETAIL_SERVICE_ID,
    };

    public BookingDetailDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public BookingDetail insert(BookingDetail bookingDetail) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID, bookingDetail.getBookingId());
        values.put(DatabaseHelper.COLUMN_BOOKING_DETAIL_SERVICE_ID, bookingDetail.getServiceId());
        long insertId = db.insert(DatabaseHelper.BOOKING_DETAIL_TABLE, null, values);
        bookingDetail.setId((int) insertId);
        return bookingDetail;
    }

    public List<Integer> getListServiceByBookingId(int bookingId) {
        db = dbHelper.getReadableDatabase();
        List<Integer> serviceIds = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.BOOKING_DETAIL_TABLE, allColumns, DatabaseHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            serviceIds.add(cursor.getInt(2));
            cursor.moveToNext();
        }
        cursor.close();
        return serviceIds;
    }

    public List<BookingDetail> getAllBookingDetail() {
        db = dbHelper.getReadableDatabase();
        List<BookingDetail> bookingDetails = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.BOOKING_DETAIL_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BookingDetail bookingDetail = cursorToBookingDetail(cursor);
            bookingDetails.add(bookingDetail);
            cursor.moveToNext();
        }
        cursor.close();
        return bookingDetails;
    }

    public List<BookingDetail> getBookingDetailByBookingId(int bookingId) {
        db = dbHelper.getReadableDatabase();
        List<BookingDetail> bookingDetails = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.BOOKING_DETAIL_TABLE, allColumns, DatabaseHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BookingDetail bookingDetail = cursorToBookingDetail(cursor);
            bookingDetails.add(bookingDetail);
            cursor.moveToNext();
        }
        cursor.close();
        return bookingDetails;
    }

    public void deleteByBookingId(int bookingId) {
        db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.BOOKING_DETAIL_TABLE, DatabaseHelper.COLUMN_BOOKING_DETAIL_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
    }

    private BookingDetail cursorToBookingDetail(Cursor cursor) {
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setId(cursor.getInt(0));
        bookingDetail.setBookingId(cursor.getInt(1));
        bookingDetail.setServiceId(cursor.getInt(2));
        return bookingDetail;
    }

}
