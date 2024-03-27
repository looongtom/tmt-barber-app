package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Account;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION=1;


    // TABLE ACCOUNT
    public static final String ACCOUNT_TABLE = "ACCOUNT";
    public static final String COLUMN_ACCOUNT_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_PHONENUMBER = "PHONE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_DATEOFBIRTH = "DATE_OF_BIRTH";
    public static final String COLUMN_ABOUT = "ABOUT";
    public static final String COLUMN_ACCOUNT_FILE_PICTURE = "AVATAR";
    public static final String COLUMN_FOREIGN_ROLEID = "roleID";

    public static final String COLUMN_IS_BLOCK = "IS_BLOCK";

    //TABLE ROLE
    public static final String ROLE_TABLE = "ROLE";
    public static final String COLUMN_ROLE_ID = "ID";
    public static final String COLUMN_ROLE_NAME = "ROLENAME";

    //TABLE CATEGORIES
    public static final String CATEGORIES_TABLE = "CATEGORIES";
    public static final String COLUMN_CATEGORY_ID = "ID";
    public static final String COLUMN_CATEGORY_NAME = "NAME";
    public static final String COLUMN_CATEGORY_DESCRIPTION = "DESCRIPTION";

    public static final String COLUMN_CATEGORY_FILE_PICTURE = "FILE_PICTURE";

    //TABLE SERVICES
    public static final String SERVICES_TABLE = "SERVICES";
    public static final String COLUMN_SERVICE_ID = "ID";
    public static final String COLUMN_SERVICE_NAME = "NAME";

    public static  final String COLUMN_SERVICE_PRICE = "PRICE";
    public static  final String COLUMN_SERVICE_DESCRIPTION = "DESCRIPTION";
    public static  final String COLUMN_SERVICE_FILE = "FILE";
//    public static final String COLUMN_SERVICE_CATEGORY_ID = "CATEGORY_ID";

    //TABLE BOOKING
    public static final String BOOKING_TABLE = "BOOKING";
    public static final String COLUMN_BOOKING_ID = "ID";
    public static final String COLUMN_BOOKING_USER_ID = "USER_ID";
    public static final String COLUMN_BOOKING_BARBER_ID = "BARBER_ID";
    public static final String COLUMN_BOOKING_TIME = "BOOKING_TIME";
    public static final String COLUMN_BOOKING_CREATE_TIME = "CREATE_TIME";
    public static final String COLUMN_BOOKING_SLOT = "SLOT";
    public static final String COLUMN_BOOKING_TOTAL = "TOTAL";

    public static final String COLUMN_BOOKING_STATUS = "STATUS";

    //TABLE BOOKING_DETAIL
    public static final String BOOKING_DETAIL_TABLE = "BOOKING_DETAIL";
    public static final String COLUMN_BOOKING_DETAIL_ID = "ID";
    public static final String COLUMN_BOOKING_DETAIL_BOOKING_ID = "BOOKING_ID";
    public static final String COLUMN_BOOKING_DETAIL_SERVICE_ID = "SERVICE_ID";

    //Table TimeSlot
    public static final String TIME_SLOT_TABLE = "TIME_SLOT";
    public static final String COLUMN_TIME_SLOT_ID = "ID";
    public static final String COLUMN_TIME_START_TABLE = "TIME_START";
    public static final String COLUMN_STATUS_TABLE = "STATUS";
    public static final String COLUMN_DATE_TABLE = "DATE";
    public static final String COLUMN_BARBER_ID = "BARBER_ID";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "barber.db", null, DATABASE_VERSION);
    }
    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //ACCOUNT
        createAccountTable(db);
        insertAccountTable(db);
        //ROLE
        createRoleTable(db);
        insertRolesTable(db);
        //CATEGORIES
        createCategoriesTable(db);
        insertCategoriesTable(db);
        //SERVICES
        createServicesTable(db);
        insertServicesTable(db);
        //VOUCHERS
        //BOOKING
        createBookingTable(db);
        insertBookingTable(db);
        //BOOKING DETAIL
        createBookingDetailTable(db);
        //TIME SLOT
        createTimeSlotTable(db);
        insertTimeSlotTable(db);
    }

    private void insertTimeSlotTable(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

        values.put(COLUMN_TIME_START_TABLE, "9:00");
        values.put(COLUMN_STATUS_TABLE, "Available");
        values.put(COLUMN_DATE_TABLE, "27-03-2024");
        values.put(COLUMN_BARBER_ID, 1);
        db.insert(TIME_SLOT_TABLE, null, values);

    }

    private void createTimeSlotTable(SQLiteDatabase db) {
        String createTableTimeSlot = "CREATE TABLE IF NOT EXISTS " + TIME_SLOT_TABLE + " (" +
                COLUMN_TIME_SLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME_START_TABLE + " TEXT, " +
                COLUMN_STATUS_TABLE + " INTEGER, " +
                COLUMN_DATE_TABLE + " TEXT, " +
                COLUMN_BARBER_ID + " INTEGER REFERENCES " + ACCOUNT_TABLE + "(" + COLUMN_ACCOUNT_ID + ")" +
                ")";
        db.execSQL(createTableTimeSlot);
    }

    //this is called if the database version number changes, It prevents previous users app from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ROLE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SERVICES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIME_SLOT_TABLE);
        // Tạo lại bảng mới với cấu trúc đã cập nhật
        onCreate(db);
    }

    public void createAccountTable(SQLiteDatabase db) {
        String createTableAccount = "CREATE TABLE IF NOT EXISTS " + ACCOUNT_TABLE + " (" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PHONENUMBER + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_DATEOFBIRTH + " TEXT, " +
                COLUMN_ACCOUNT_FILE_PICTURE + " TEXT, " +
                COLUMN_IS_BLOCK + " INTEGER, " +
                COLUMN_FOREIGN_ROLEID + " INTEGER REFERENCES " + ROLE_TABLE + "("+ COLUMN_ROLE_ID + ")," +
                COLUMN_ABOUT + " TEXT" +
                ")";
        db.execSQL(createTableAccount);
    }
    public void createRoleTable(SQLiteDatabase db) {
        String createTableRole = "CREATE TABLE IF NOT EXISTS " + ROLE_TABLE + " (" + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROLE_NAME + " TEXT ) ";

        db.execSQL(createTableRole);
    }

    public void createCategoriesTable(SQLiteDatabase db) {
        String createTableCategories = "CREATE TABLE IF NOT EXISTS " + CATEGORIES_TABLE + " (" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_CATEGORY_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY_FILE_PICTURE + " TEXT )";

        db.execSQL(createTableCategories);
    }

    public void  insertCategoriesTable(SQLiteDatabase db) {
        String sql = "";
        sql = "INSERT INTO " + CATEGORIES_TABLE + " VALUES (null, 'CẮT GỘI MASSAGE', 'abcd', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + CATEGORIES_TABLE + " VALUES (null, 'COMBO CHĂM SÓC DA - THƯ GIÃN', 'abcs', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + CATEGORIES_TABLE + " VALUES (null, 'UỐN HÀN QUỐC 8 CẤP ĐỘ', 'abcdef', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
    }

    public void createServicesTable(SQLiteDatabase db) {
        String createTableServices = "CREATE TABLE IF NOT EXISTS " + SERVICES_TABLE + " (" + COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_NAME + " TEXT, " +
                COLUMN_SERVICE_PRICE + " TEXT, " +
                COLUMN_SERVICE_DESCRIPTION + " TEXT, " +
                COLUMN_SERVICE_FILE + " TEXT"+ ")";
//                COLUMN_SERVICE_CATEGORY_ID + " INTEGER REFERENCES " + CATEGORIES_TABLE + "("+ COLUMN_CATEGORY_ID + "))";

        db.execSQL(createTableServices);
    }

    public void createBookingTable(SQLiteDatabase db) {
        String createTableBooking = "CREATE TABLE IF NOT EXISTS " + BOOKING_TABLE + " (" + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOKING_USER_ID + " INTEGER REFERENCES " + ACCOUNT_TABLE + "(" + COLUMN_ACCOUNT_ID + "), " +
                COLUMN_BOOKING_BARBER_ID + " INTEGER REFERENCES " + ACCOUNT_TABLE + "(" + COLUMN_ACCOUNT_ID + "), " +
                COLUMN_BOOKING_TIME + " TEXT, " +
                COLUMN_BOOKING_SLOT + " TEXT, " +
                COLUMN_BOOKING_STATUS + " TEXT, " +
                COLUMN_BOOKING_TOTAL + " TEXT, " +
                COLUMN_BOOKING_CREATE_TIME + " TEXT)";
        db.execSQL(createTableBooking);
    }
    public void insertBookingTable(SQLiteDatabase db) {
        String sql = "";
        sql = "INSERT INTO " + BOOKING_TABLE + " VALUES (null,1, 3, '22-07-2023', 4, null, '22-07-2023', 10000)";
        db.execSQL(sql);
        sql = "INSERT INTO " + BOOKING_TABLE + " VALUES (null,2, 3, '22-07-2023', 5, null, '22-07-2023', 10000)";
        db.execSQL(sql);
    }

    public void createBookingDetailTable(SQLiteDatabase db) {
        String createTableBookingDetail = "CREATE TABLE IF NOT EXISTS " + BOOKING_DETAIL_TABLE + " (" + COLUMN_BOOKING_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOKING_DETAIL_BOOKING_ID + " INTEGER REFERENCES " + BOOKING_TABLE + "(" + COLUMN_BOOKING_ID + "), " +
                COLUMN_BOOKING_DETAIL_SERVICE_ID + " INTEGER REFERENCES " + SERVICES_TABLE + "(" + COLUMN_SERVICE_ID + "))";
        db.execSQL(createTableBookingDetail);
    }

    public void insertServicesTable(SQLiteDatabase db) {
        String sql = "";
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Cắt gội 10 bước', 70000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Combo cắt gội và massage đá nóng VIP', 120000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Combo cắt gội VIP (all dịch vụ chăm sóc)', 270000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Chăm sóc da cấp thiết UltraWhite', 50000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Massage cổ, vai, gáy bạc hà cam ngọt', 45000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Combo lấy ráy tai VIP', 70000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Uốn cao cấp Hàn Quốc', 399000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Uốn định hình Ivy Star 2023', 599000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
        sql = "INSERT INTO " + SERVICES_TABLE + " VALUES (null, 'Uốn tiêu chuẩn', 319000, '', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689578157/jiuar6v7kwbza3czwebw.png')";
        db.execSQL(sql);
    }


    public boolean addOne(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, account.getUsername());
        cv.put(COLUMN_PASSWORD, account.getPassword());
        db.insert(ACCOUNT_TABLE, null, cv);
        return true;
    }

    public void insertRolesTable(SQLiteDatabase db) {
        String sql = "";
        sql = "INSERT INTO " + ROLE_TABLE + " VALUES (null,'admin')";
        db.execSQL(sql);
        sql = "INSERT INTO " + ROLE_TABLE + " VALUES (null,'staff')";
        db.execSQL(sql);
        sql = "INSERT INTO " + ROLE_TABLE + " VALUES (null, 'user')";
        db.execSQL(sql);
    }

    public void insertAccountTable(SQLiteDatabase db) {
        //admin
        String sql = "";
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'ADMIN', 'admin', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png', 0, 1,'' )";
        db.execSQL(sql);

        //staff
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'Minh Tuấn', 'tuan', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png', 0, 2,'about me' )";
        db.execSQL(sql);
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'Phong BVB', 'phong', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png', 0, 2,'about me' )";
        db.execSQL(sql);
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'Liêm Barber', 'liem', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png', 0, 2,'about me' )";
        db.execSQL(sql);
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'Hào Napoli', 'hao', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', null, 0, 2,'about me' )";
        db.execSQL(sql);

        //user
        sql = "INSERT INTO " + ACCOUNT_TABLE + " VALUES (null,'USER', 'user', '1', '0901248851', 'admin@gmail.com', 'Nam', '22-07-2001', 'https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png', 0, 3,'about me' )";
        db.execSQL(sql);
    }

}
