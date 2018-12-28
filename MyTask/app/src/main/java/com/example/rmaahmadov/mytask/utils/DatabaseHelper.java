package com.example.rmaahmadov.mytask.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myusers.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";
    public static final String COL_4 = "pin";
    





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (ID INTEGER PRIMARY KEY AUTOINCREMENT,email VARCHAR,password VARCHAR,pin NUMBER)");
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }





    public long addUser(String email, String password, int pin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("pin", pin);
        long res = db.insert("users", null, contentValues);
        db.close();
        return res;
    }





    public boolean checkUserAndMovePinPage(String username, String password) {
        String[] colums = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " AND " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, colums, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }





    public boolean checkUserPin(int pin){
        String[] colums = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_4 + "=?";
        String[] selectionArgs = {String.valueOf(pin)};
        Cursor cursor = db.query(TABLE_NAME, colums, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
