package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Service;

import java.util.ArrayList;

public class ServiceDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static String[] allColumns = {
            DatabaseHelper.COLUMN_SERVICE_ID,
            DatabaseHelper.COLUMN_SERVICE_NAME,
            DatabaseHelper.COLUMN_SERVICE_PRICE,
            DatabaseHelper.COLUMN_SERVICE_DESCRIPTION,
            DatabaseHelper.COLUMN_SERVICE_FILE
    };

    public ServiceDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public static ArrayList<Service> selectAllService(Context context) {
        ArrayList<Service> services = new ArrayList<Service>();
        ServiceDataSource serviceDataSource = new ServiceDataSource(context);
        serviceDataSource.open();
        Cursor cursor = db.query(DatabaseHelper.SERVICES_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Service service = new Service();
                service.setId(cursor.getInt(0));
                service.setName(cursor.getString(1));
                service.setPrice(cursor.getDouble(2));
                service.setDescription(cursor.getString(3));
                service.setFilePath(cursor.getString(4));
                services.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return services;
    }

    public Service addService(Service service) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SERVICE_NAME, service.getName());
        values.put(DatabaseHelper.COLUMN_SERVICE_PRICE, service.getPrice());
        values.put(DatabaseHelper.COLUMN_SERVICE_DESCRIPTION, service.getDescription());
        values.put(DatabaseHelper.COLUMN_SERVICE_FILE, service.getFilePath());
        long insertId = db.insert(DatabaseHelper.SERVICES_TABLE, null, values);
        Cursor cursor = db.query(DatabaseHelper.SERVICES_TABLE, allColumns, DatabaseHelper.COLUMN_SERVICE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Service newService = cursorToService(cursor);
        cursor.close();
        return newService;
    }

    private Service cursorToService(Cursor cursor) {
        Service serv = new Service();
        serv.setId(cursor.getInt(0));
        serv.setName(cursor.getString(1));
        serv.setPrice(cursor.getDouble(2));
        serv.setDescription(cursor.getString(3));
        serv.setFilePath(cursor.getString(4));

        return serv;
    }

    public int updateService(Service service) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_SERVICE_NAME, service.getName());
        cv.put(dbHelper.COLUMN_SERVICE_DESCRIPTION, service.getDescription());
        cv.put(dbHelper.COLUMN_SERVICE_PRICE, service.getPrice());
        cv.put(dbHelper.COLUMN_SERVICE_FILE, service.getFilePath());

        String whereCLase = "id=?";
        String[] whereArgs = {service.getId() + ""};
        return db.update(dbHelper.SERVICES_TABLE, cv, whereCLase, whereArgs);
    }

    public Service getById(int id) {
        db = dbHelper.getWritableDatabase();
        Cursor rs = db.query(dbHelper.SERVICES_TABLE, allColumns, dbHelper.COLUMN_SERVICE_ID + "= ?", new String[]{String.valueOf(id)}, null, null, null);
        if (rs != null && rs.moveToNext()) {
            Service service = cursorToService(rs);
            return service;
        }
        return null;
    }

//    public static boolean deleteService(Context context, int id) {
//        dbHelper = new DatabaseHelper(context);
//        db = dbHelper.getWritableDatabase();
//        int deleteService = db.delete(dbHelper.SERVICES_TABLE, dbHelper.COLUMN_SERVICE_ID + " = ?", new String[]{Integer.toString(id)});
//        return deleteService > 0;
//    }
}
