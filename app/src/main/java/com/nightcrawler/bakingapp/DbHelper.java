package com.nightcrawler.bakingapp;

//
//public class dishDbHelper extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "favDish.db";
//
//    // If you change the database schema, you must increment the database version
//    private static final int DATABASE_VERSION = 1;
//
//    // Constructor
//    public dishDbHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//
//        // Create a table to hold waitlist data
//        final String SQL_CREATE_STEPLIST_TABLE = "CREATE TABLE "
//                + dishContract.TABLE_NAME + " (" +
//                dishContract.STEPS + " TEXT  "
//                    +"); ";
//
//        sqLiteDatabase.execSQL(SQL_CREATE_STEPLIST_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        // For now simply drop the table and create a new one. This means if you change the
//        // DATABASE_VERSION the table will be dropped.
//        // In a production app, this method might be modified to ALTER the table
//        // instead of dropping it, so that existing data is not deleted.
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + dishContract.TABLE_NAME);
//        onCreate(sqLiteDatabase);
//    }
//
//}


//package me.indiandollar.apps.todoappcollectionwidget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import com.nightcrawler.bakingapp.Contract;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoItems.db";
    private static String SQL_DROP_TABLE;
    private static String SQL_CREATE_TABLE;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        SQL_CREATE_TABLE = "CREATE TABLE " +
                Contract.TABLE_NAME + "(" +
                Contract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.COL_TODO_TEXT + " TEXT NOT NULL" +
                ")";

        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Contract.TABLE_NAME;

        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);

    }
}


