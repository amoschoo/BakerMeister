package com.example.android.resources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Amos on 11/7/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USERINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE "+ "SKILLTREE"+"("+"RECIPENAME" + " TEXT," + "STATUS" + " TEXT);";
    private static final String CREATE_QUERY2 =
            "CREATE TABLE "+ "INVENTORY"+"("+"SHELF" + " TEXT," + "ITEMNAME" + " TEXT,"+"ADJUSTMENT"+ " TEXT);";
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATEBASE OPERATIONS", "Database created/ opened...");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        db.execSQL(CREATE_QUERY2);
        Log.e("DATABASE OPERATIONS", "Table created...");
        String shelf1 = "INSERT INTO "+"INVENTORY"+ "("
                + "SHELF,"+"ITEMNAME,"+"ADJUSTMENT"+")"
                + "VALUES('shelf1','empty','0.0')";
        String shelf2 = "INSERT INTO "+"INVENTORY"+ "("
                + "SHELF,"+"ITEMNAME,"+"ADJUSTMENT" +")"
                + "VALUES('shelf2','empty','0.0')";
        String shelf3 = "INSERT INTO "+"INVENTORY"+ "("
                + "SHELF,"+"ITEMNAME,"+"ADJUSTMENT" +")"
                + "VALUES('shelf3','empty','0.0')";
        String level = "INSERT INTO "+"INVENTORY"+ "("
                + "SHELF,"+"ITEMNAME" +")"
                + "VALUES('skilllevel','0')";
        db.execSQL(shelf1);
        db.execSQL(shelf2);
        db.execSQL(shelf3);
        db.execSQL(level);


    }
    public void addskills(String name, String status, SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("RECIPENAME", name);
        contentValues.put("STATUS", status);
        db.insert("SKILLTREE", null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");

    }
    public Cursor getskills(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {"RECIPENAME","STATUS"};
        cursor = db.query("SKILLTREE",projections, null, null, null, null, null);
        return cursor;
    }
    public int updateskill(String name, String status, SQLiteDatabase sqLiteDatabase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("STATUS",status);
        String selection = "RECIPENAME" + " LIKE ?";
        String[] selection_args = {name};
        int count = sqLiteDatabase.update("SKILLTREE",contentValues,selection,selection_args);
        return count;
    }

    public void addinventory(String shelf, String name, SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SHELF",shelf);
        contentValues.put("ITEMNAME", name);
        db.insert("INVENTORY", null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");

    }
    public Cursor getinventory(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {"SHELF","ITEMNAME"};
        cursor = db.query("INVENTORY",projections, null, null, null, null, null);
        return cursor;
    }
    public void updateinventory(String shelf, String name, SQLiteDatabase sqLiteDatabase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ITEMNAME",name);
        String selection = "SHELF" + " LIKE ?";
        String[] selection_args = {shelf};
        sqLiteDatabase.update("INVENTORY",contentValues,selection,selection_args);
    }
    public void updateadjustment(String shelf, String name, SQLiteDatabase sqLiteDatabase)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ADJUSTMENT",name);
        String selection = "SHELF" + " LIKE ?";
        String[] selection_args = {shelf};
        sqLiteDatabase.update("INVENTORY",contentValues,selection,selection_args);
    }
    public Cursor getItem(String shelf, SQLiteDatabase db){
        String[] projections = {"ITEMNAME","ADJUSTMENT"};
        String selection = "SHELF" + " LIKE ?";
        String[] selection_args = {shelf};
        Cursor cursor = db.query("INVENTORY",projections,selection,selection_args,null,null,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
