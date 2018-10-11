package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "record_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "CREATED";
    public static final String COL_5 = "UPDATED";

    public String ID;

    public static String id1,title,desc;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        sdf.format(date);

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY,TITLE TEXT,DESCRIPTION TEXT,CREATED DATETIME DEFAULT CURRENT_TIMESTAMP,UPDATED DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Insert Data..
    public boolean insertData(String Id, String Title, String Description) {

        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String t= sdf.format(date);

        long result;
        ID = Id;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Id);
        contentValues.put(COL_2, Title);
        contentValues.put(COL_3,Description);
        contentValues.put(COL_4,t);
        contentValues.put(COL_5,t);

         result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //To Get All Data in Table..
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    //To Update Data..
    public boolean updateData(String Id,String Title, String Desc) {
        //String timeStamp = new SimpleDateFormat("dd/mm/yyyy HH:mm").format(Calendar.getInstance().getTime());

        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a");
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        sdf.format(date);


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Title);
        contentValues.put(COL_3, Desc);
        contentValues.put(COL_5,sdf.format(date));
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{Id});
        return true;
    }

    //To Delete Data..
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    //To Get Single Data(Condition Base)..
    public Cursor getData(String id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=?", new String[]{id + ""});
            if (cursor != null && cursor.moveToFirst()) {
                id1 = cursor.getString(cursor.getColumnIndex(COL_1));
                title = cursor.getString(cursor.getColumnIndex(COL_2));
                desc = cursor.getString(cursor.getColumnIndex(COL_3));

                Log.d("Single Query:", "ID: " + id +" \nTitle: " + title+"\nDescription: "+desc);

            }
            return cursor;
        } finally {
            cursor.close();
        }
    }
}