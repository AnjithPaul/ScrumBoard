package com.ap.scrumboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScrumDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "scrum";
    private static final int DB_VERSION = 1;

    ScrumDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db,0,DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        updateMyDatabase(db,oldVersion,newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion) {

        if (oldVersion < 1) {
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.PARENT_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.PARENT_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.PARENT_TASK + " TEXT);");
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.SUB_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.SUB_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.SUB_TASK + " TEXT,"+ Contract.PARENT+" TEXT,"+ Contract.STATUS + "TEXT,"+Contract.EMPLOYEE +"TEXT);");

        }
    }


}
