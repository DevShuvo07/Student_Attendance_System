package com.example.student;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {
    public static final String Databasename = "Student.db";
    public static final String Tablename = "studenttable";
    public static final String col_1 = "ID";
    public static final String col_2 = "Name";
    public static final String col_3 = "PROGRAM";
    public static final String col_4 = "INTAKE";
    public static final String col_5 = "SECTION";
    public static final String col_6 = "COURSE_CODE";

    public database(@Nullable Context context) {
        super(context, Databasename, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Tablename + " (ID INTEGER , NAME TEXT , PROGRAM TEXT , INTAKE INTEGER , SECTION INTEGER, COURSE_CODE STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Tablename);
        onCreate(db);
    }
    public java.util.List<String> getprogram(){
        java.util.List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT PROGRAM FROM " + Tablename;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if(cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                // Add province name to arraylist
                String pname = cursor.getString(0);
                list.add(pname);

            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
    public java.util.List<String> getintakedata(){
        java.util.List<String> intakedata = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT INTAKE FROM " + Tablename;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                intakedata.add(cursor.getString(0));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return intakedata;
    }

    public java.util.List<String> getcourse_code(String programName) {

        java.util.List<String> course_code_List = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT " +col_6+ " FROM " + Tablename + " WHERE " + col_3 + "='" +programName+ "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            course_code_List.add(cursor.getString(0));
        }

        // closing connection
        cursor.close();

        return course_code_List;
    }
    public java.util.List<String> getintake(String course_code) {

        java.util.List<String> intakelist = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT " +col_4+ " FROM " + Tablename + " WHERE " +col_6+"='" +course_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            intakelist.add(cursor.getString(0));
        }

        // closing connection
        cursor.close();

        return intakelist;
    }
    public java.util.List<String> getsection(String studentIntake) {

        java.util.List<String> sectionlist = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT " +col_5+ " FROM " + Tablename + " WHERE " +col_4+"='" +studentIntake+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            sectionlist.add(cursor.getString(0));
        }

        // closing connection
        cursor.close();

        return sectionlist;
    }
}

