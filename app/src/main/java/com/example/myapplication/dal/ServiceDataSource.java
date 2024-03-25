package com.example.myapplication.dal;

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

    public static ArrayList<Service> selectAllService(Context context){
        ArrayList<Service> services = new ArrayList<Service>();
        ServiceDataSource serviceDataSource = new ServiceDataSource(context);
        serviceDataSource.open();
        Cursor cursor = db.query(DatabaseHelper.SERVICES_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            do{
                Service service = new Service();
                service.setId(cursor.getInt(0));
                service.setName(cursor.getString(1));
                service.setPrice(cursor.getDouble(2));
                service.setDescription(cursor.getString(3));
                service.setFilePath(cursor.getString(4));
                services.add(service);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return services;
    }
}
