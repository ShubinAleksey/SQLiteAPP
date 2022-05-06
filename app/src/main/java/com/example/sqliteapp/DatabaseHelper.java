package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table UserInfo(ID_User INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " +
                "name TEXT, phone TEXT, date_of_birth TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists UserInfo");
    }

    //Вставка
    public Boolean insert(String name, String phone, String date_of_birth) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("date_of_birth", date_of_birth);
        long result = DB.insert("UserInfo", null, contentValues);
        return result != -1;
    }

    //Обновление
    public Boolean edit(String name, String new_name, String new_phone, String new_date_of_birth) {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",new_name);
        values.put("phone",new_phone);
        values.put("date_of_birth",new_date_of_birth);
        int result = DB.update("UserInfo",
                values,"name = " + "'" + name + "'",null);
        DB.close();
        return result > 0;
    }

    //Удаление
    public Boolean delete(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("UserInfo",
                "name = " + "'" + name + "'", null
        );
        DB.close();
        return result > 0;
    }

    //Просмотр
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from UserInfo", null);
    }
}
