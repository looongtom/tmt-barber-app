package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Result;

public class ResultDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_RESULT_ID,
            DatabaseHelper.COLUMN_IMAGE_FRONT,
            DatabaseHelper.COLUMN_IMAGE_BACK,
            DatabaseHelper.COLUMN_IMAGE_LEFT,
            DatabaseHelper.COLUMN_IMAGE_RIGHT
    };

    public ResultDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public Result getById(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.RESULT_TABLE, allColumns, DatabaseHelper.COLUMN_RESULT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            Result result = cursorToResult(cursor);
            return result;
        }
        return null;
    }

    public Result insertResult(Context context, Result result) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_IMAGE_FRONT, result.getImageFront());
        values.put(DatabaseHelper.COLUMN_IMAGE_BACK, result.getImageBack());
        values.put(DatabaseHelper.COLUMN_IMAGE_LEFT, result.getImageLeft());
        values.put(DatabaseHelper.COLUMN_IMAGE_RIGHT, result.getImageRight());
        long insertId = db.insert(DatabaseHelper.RESULT_TABLE, null, values);
        Cursor cursor = db.query(DatabaseHelper.RESULT_TABLE, allColumns, DatabaseHelper.COLUMN_RESULT_ID + " = ?", new String[]{String.valueOf(insertId)}, null, null, null);
        cursor.moveToFirst();
        Result newResult = cursorToResult(cursor);
        cursor.close();
        return newResult;
    }

    public long updateResult(Context context, Result result) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (result.getImageFront() != null)
            values.put(DatabaseHelper.COLUMN_IMAGE_FRONT, result.getImageFront());
        if (result.getImageBack() != null)
            values.put(DatabaseHelper.COLUMN_IMAGE_BACK, result.getImageBack());
        if (result.getImageLeft() != null)
            values.put(DatabaseHelper.COLUMN_IMAGE_LEFT, result.getImageLeft());
        if (result.getImageRight() != null)
            values.put(DatabaseHelper.COLUMN_IMAGE_RIGHT, result.getImageRight());
        return db.update(DatabaseHelper.RESULT_TABLE, values, DatabaseHelper.COLUMN_RESULT_ID + " = ?", new String[]{String.valueOf(result.getId())});
    }

    private Result cursorToResult(Cursor cursor) {
        Result result = new Result();
        result.setId(cursor.getInt(0));
        result.setImageFront(cursor.getString(1));
        result.setImageBack(cursor.getString(2));
        result.setImageLeft(cursor.getString(3));
        result.setImageRight(cursor.getString(4));
        return result;
    }
}
