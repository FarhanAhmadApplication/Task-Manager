package com.fayez.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskManagerDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="TaskManager.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Task_Manager";
    private static final String ID="id";
    private static final String TASK_NAME="task_name";
    private static final String TASK_DETAILS="task_details";
    private static final String STARTING_DATE="starting_date";
    private static final String ENDING_DATE="ending_date";
    private static final String TASK_STATUS="task_status";
    public TaskManagerDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query=" CREATE TABLE "+TABLE_NAME +" ( "
                + ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TASK_NAME+ " TEXT,"
                +TASK_DETAILS +" TEXT,"
                +STARTING_DATE +" TEXT,"
                +ENDING_DATE+ " TEXT,"
                +TASK_STATUS+ " TEXT"+")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    void insert(String tn,String td,String sd,String ed,String ts){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TASK_NAME,tn);
        cv.put(TASK_DETAILS,td);
        cv.put(STARTING_DATE,sd);
        cv.put(ENDING_DATE,ed);
        cv.put(TASK_STATUS,ts);
        db.insert(TABLE_NAME,null,cv);
    }
    Cursor showdata(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=null;
        if(db!=null)
            cursor=db.rawQuery(query,null);
        return cursor;
    }
    void update(String tn,String td,String sd,String ed,String ts,String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TASK_NAME,tn);
        cv.put(TASK_DETAILS,td);
        cv.put(STARTING_DATE,sd);
        cv.put(ENDING_DATE,ed);
        cv.put(TASK_STATUS,ts);
        db.update(TABLE_NAME,cv,ID+"=?",new String[]{row_id});
    }
    void delete(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{row_id});
    }
    Cursor showSearchedData(String s){
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TASK_NAME + " LIKE'"+s+"%'";
        Cursor cursor=null;
        if(db!=null)
            cursor = db.rawQuery(query, null);
        return cursor;
    }
    void deleteAll(){
        String query="DROP TABLE " +TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query);
        onCreate(db);
    }
}
