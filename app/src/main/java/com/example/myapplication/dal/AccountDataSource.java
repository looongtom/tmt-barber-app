package com.example.myapplication.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplication.model.Account;

import java.util.ArrayList;

public class AccountDataSource {
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private String[] allColumns = {
            dbHelper.COLUMN_ACCOUNT_ID,
            dbHelper.COLUMN_NAME,
            dbHelper.COLUMN_USERNAME,
            dbHelper.COLUMN_PASSWORD,
            dbHelper.COLUMN_EMAIL,
            dbHelper.COLUMN_PHONENUMBER,
            dbHelper.COLUMN_GENDER,
            dbHelper.COLUMN_DATEOFBIRTH,
            dbHelper.COLUMN_FOREIGN_ROLEID,
            dbHelper.COLUMN_IS_BLOCK,
            dbHelper.COLUMN_ABOUT,

    };

    public AccountDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static ArrayList<Account> selectAccountsRoleUser(Context context) {
        ArrayList<Account> accounts = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.ACCOUNT_TABLE + " WHERE " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 3";
        //SELECT * FROM ACCOUNT WHERE roleID = 3

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

//        boolean isBlock = false;
//        int columnIndex = cursor.getColumnIndex(dbHelper.COLUMN_IS_BLOCK);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int accountID = cursor.getInt(0);
                String name = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String phoneNumber = cursor.getString(4);
                String email = cursor.getString(5);
                String gender = cursor.getString(6);
                String dateOfBirth = cursor.getString(7);
                String image = cursor.getString(8);
//                isBlock = cursor.getInt(columnIndex) == 1;
                boolean is_block =cursor.getInt(9) == 1;
                int role_ID = cursor.getInt(10);
                String about = cursor.getString(11);

//                boolean is_block = cursor.get

                // Create a Category object
                Account account = new Account(accountID, name, username, password, email, phoneNumber, dateOfBirth, gender, image, is_block, role_ID);

                // Add the category to the list
                accounts.add(account);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return accounts;
    }

    public Account addAccount(Account account){
        open();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_NAME, account.getName());
        cv.put(dbHelper.COLUMN_USERNAME, account.getUsername());
        cv.put(dbHelper.COLUMN_PASSWORD, account.getPassword());
        cv.put(dbHelper.COLUMN_EMAIL, account.getEmail());
        cv.put(dbHelper.COLUMN_PHONENUMBER, account.getPhone());
        cv.put(dbHelper.COLUMN_DATEOFBIRTH, account.getDateOfBirth());
        cv.put(dbHelper.COLUMN_FOREIGN_ROLEID, account.getRoleId());// barber = 2, user = 3
        cv.put(dbHelper.COLUMN_GENDER, account.getGender());
        cv.put(dbHelper.COLUMN_ABOUT, account.getAbout());
        cv.put(dbHelper.COLUMN_ACCOUNT_FILE_PICTURE, "https://res.cloudinary.com/dgm68hajt/image/upload/v1689830191/user_ppwwwc.png");
        long insertId = db.insert(dbHelper.ACCOUNT_TABLE, null, cv);
        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, allColumns, null,null,null,null,null );
        cursor.moveToFirst();
        Account newAccount = cursorToAccount(cursor);
        cursor.close();
        return newAccount;
    }

    private Account cursorToAccount(Cursor cursor) {
        Account account = new Account();
        account.setId(cursor.getInt(0));
        account.setName(cursor.getString(1));
        account.setUsername(cursor.getString(2));
        account.setPassword(cursor.getString(3));
        account.setEmail(cursor.getString(4));
        account.setPhone(cursor.getString(5));
        account.setGender(cursor.getString(6));
        account.setDateOfBirth(cursor.getString(7));
        account.setRoleId(cursor.getInt(8));
        account.setAbout(cursor.getString(9));
        return account;
    }

    public boolean checkAccount(String username, String password) {
        db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.COLUMN_ACCOUNT_ID,dbHelper.COLUMN_USERNAME,dbHelper.COLUMN_PASSWORD};
        String selection = dbHelper.COLUMN_USERNAME + " = ? AND " + dbHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    public int getUserIdByUsername(String username) {
        int userId = -1;
        db = dbHelper.getReadableDatabase();
        // Tạo câu truy vấn SQL để lấy ID người dùng dựa vào tên người dùng
        String query = "SELECT " + dbHelper.COLUMN_ACCOUNT_ID + " FROM " + dbHelper.ACCOUNT_TABLE +
                " WHERE " + dbHelper.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor.moveToFirst()) {
            // Nếu có kết quả từ câu truy vấn, lấy ID người dùng từ cột COLUMN_ACCOUNT_ID
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ACCOUNT_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }
    public String getUserByUserId(int userId) {
        String name = "";
        db = dbHelper.getReadableDatabase();
        // Tạo câu truy vấn SQL để lấy ID người dùng dựa vào tên người dùng
        String query = "SELECT " + dbHelper.COLUMN_NAME + " FROM " + dbHelper.ACCOUNT_TABLE +
                " WHERE " + dbHelper.COLUMN_ACCOUNT_ID + " = ?" + "AND " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 3";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            // Nếu có kết quả từ câu truy vấn, lấy ID người dùng từ cột COLUMN_ACCOUNT_ID
            name = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return name;
    }

    public Account getAccountById(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, allColumns, dbHelper.COLUMN_ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Account account = cursorToAccount(cursor);
        cursor.close();
        db.close();
        return account;
    }

    public String getStaffByStaffId(int staffId) {
        String name = "";
        db = dbHelper.getReadableDatabase();
        // Tạo câu truy vấn SQL để lấy ID người dùng dựa vào tên người dùng
        String query = "SELECT " + dbHelper.COLUMN_NAME + " FROM " + dbHelper.ACCOUNT_TABLE +
                " WHERE " + dbHelper.COLUMN_ACCOUNT_ID + " = ?" + "AND " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 2";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(staffId)});
        if (cursor.moveToFirst()) {
            // Nếu có kết quả từ câu truy vấn, lấy ID người dùng từ cột COLUMN_ACCOUNT_ID
            name = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return name;
    }

    public int getStaffIdByUsername(String name) {
        int staffId = -1;
        db = dbHelper.getReadableDatabase();
        // Tạo câu truy vấn SQL để lấy ID người dùng dựa vào tên người dùng
        String query = "SELECT " + dbHelper.COLUMN_ACCOUNT_ID + " FROM " + dbHelper.ACCOUNT_TABLE +
                " WHERE " + dbHelper.COLUMN_NAME + " = ?" + "AND " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 2";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        if (cursor.moveToFirst()) {
            // Nếu có kết quả từ câu truy vấn, lấy ID người dùng từ cột COLUMN_ACCOUNT_ID
            staffId = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ACCOUNT_ID));
        }
        cursor.close();
        db.close();
        return staffId;
    }

    public String getFilePictureForCategory(int staffId) {
        db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.COLUMN_ACCOUNT_FILE_PICTURE};
        String selection = dbHelper.COLUMN_ACCOUNT_ID + "=?";
        String[] selectionArgs = {String.valueOf(staffId)};
        Cursor cursor = db.query(dbHelper.ACCOUNT_TABLE, projection, selection, selectionArgs, null, null, null);
        String filePicture = null;
        if (cursor.moveToFirst()) {
            filePicture = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ACCOUNT_FILE_PICTURE));
        }
        cursor.close();
        return filePicture;
    }

    public static ArrayList<Account> selectAccountsRoleStaff(Context context) {
        ArrayList<Account> accounts = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getReadableDatabase();
        // Define the SELECT query
        String query = "SELECT * FROM " + dbHelper.ACCOUNT_TABLE + " WHERE " + dbHelper.COLUMN_FOREIGN_ROLEID + " = 2";
        //SELECT * FROM ACCOUNT WHERE roleID = 1

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Iterate through the cursor and retrieve category data
        if (cursor.moveToFirst()) {
            do {
                // Retrieve column values from the cursor
                int accountID = cursor.getInt(0);
                String name = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String phoneNumber = cursor.getString(4);
                String email = cursor.getString(5);
                String gender = cursor.getString(6);
                String dateOfBirth = cursor.getString(7);
                String avatar = cursor.getString(8);
                int role_ID = cursor.getInt(9);
                String about = cursor.getString(11);
                // Create a Category object
                Account account = new Account(accountID, name, username, password, email, phoneNumber, dateOfBirth, gender, avatar, role_ID,about);
                // Add the category to the list
                accounts.add(account);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return accounts;
    }

    public static boolean updateAccount( Account account) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, account.getName());
        values.put(dbHelper.COLUMN_USERNAME, account.getUsername());
        values.put(dbHelper.COLUMN_ABOUT, account.getAbout());
        values.put(dbHelper.COLUMN_PHONENUMBER, account.getPhone());
        values.put(dbHelper.COLUMN_EMAIL, account.getEmail());
        values.put(dbHelper.COLUMN_DATEOFBIRTH, account.getDateOfBirth());
        values.put(dbHelper.COLUMN_GENDER, account.getGender());
        values.put(dbHelper.COLUMN_ACCOUNT_FILE_PICTURE, account.getAvatar());
        String selection = dbHelper.COLUMN_ACCOUNT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(account.getId())};
        int updateAccount = db.update(dbHelper.ACCOUNT_TABLE, values, selection, selectionArgs);
        return updateAccount > 0;
    }

    //Role
    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbHelper.ROLE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int roleId = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ROLE_ID));
                String roleName = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ROLE_NAME));

                Role role = new Role(roleId, roleName);
                roles.add(role);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return roles;
    }

    public Role getRoleByName(String roleName) {
        db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + dbHelper.ROLE_TABLE + " WHERE " + dbHelper.COLUMN_ROLE_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{roleName});

        Role role = null;

        if (cursor.moveToFirst()) {
            int roleId = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ROLE_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ROLE_NAME));

            role = new Role(roleId, name);
        }

        cursor.close();
        db.close();
        return role;
    }

    public int getRoleIdByAccountId(int accountId) {
        db = dbHelper.getReadableDatabase();

        // Tạo câu truy vấn SQL để lấy roleId từ bảng Account dựa vào accountId
        String query = "SELECT " + dbHelper.COLUMN_FOREIGN_ROLEID +
                " FROM " + dbHelper.ACCOUNT_TABLE +
                " WHERE " + dbHelper.COLUMN_ACCOUNT_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(accountId)});

        int roleId = -1;

        if (cursor.moveToFirst()) {
            roleId = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_FOREIGN_ROLEID));
        }

        cursor.close();
        db.close();

        return roleId;
    }


}
