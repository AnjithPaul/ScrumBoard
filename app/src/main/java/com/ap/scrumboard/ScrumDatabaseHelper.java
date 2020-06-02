package com.ap.scrumboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScrumDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "scrum";
    private static final int DB_VERSION = 15;

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

    public static void insertMain(SQLiteDatabase db,String mainTask){
        ContentValues mailValues = new ContentValues();
        mailValues.put(Contract.PARENT_TASK,mainTask);

        db.insert(Contract.PARENT_TABLE,null,mailValues);
    }

    public static void insertEmp(SQLiteDatabase db,String employee){
        ContentValues mailValues = new ContentValues();
        mailValues.put(Contract.EMPLOYEE,employee);

        db.insert(Contract.EMPLOYEE_TABLE,null,mailValues);
    }
    public static void insertSub(SQLiteDatabase db,String subTask,String parent, String status,String employee){
        ContentValues mailValues = new ContentValues();
        mailValues.put(Contract.EMPLOYEE,employee);
        mailValues.put(Contract.SUB_TASK,subTask);
        mailValues.put(Contract.PARENT,parent);
        mailValues.put("STATUS",status);

        db.insert(Contract.SUB_TABLE,null,mailValues);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion) {

        if (oldVersion < 1) {
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.PARENT_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.PARENT_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.PARENT_TASK + " TEXT);");
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.SUB_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.SUB_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.SUB_TASK + " TEXT,"+ Contract.PARENT+" TEXT, STATUS INTEGER,"+Contract.EMPLOYEE +"TEXT);");

        }
        if(oldVersion < 2){
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.EMPLOYEE_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.EMPLOYEE_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.EMPLOYEE + " TEXT);");

        }
        if(oldVersion < 3){
            insertMain(db,"Main task 1");
            insertMain(db,"Main task 2");
            insertMain(db,"Main task 3");
            insertEmp(db,"Employee 1");
            insertEmp(db,"Employee 2");
            insertEmp(db,"Employee 3");
        }
        if(oldVersion < 15){
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.SUB_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.SUB_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.SUB_TASK + " TEXT,"+ Contract.PARENT+" TEXT, STATUS TEXT,EMPLOYEE TEXT);");

            insertSub(db,"Sub task 1","Main task 1","To Do","Employee1");
            insertSub(db,"Sub task 2","Main task 1","To Do","Employee1");
            insertSub(db,"Sub task 3","Main task 1","To Do","Employee1");
            insertSub(db,"Sub task 4","Main task 2","To Do","Employee1");
            insertSub(db,"Sub task 5","Main task 2","To Do","Employee1");
            insertSub(db,"Sub task 6","Main task 2","Done","Employee1");
            insertSub(db,"Sub task ","Main task 2","Doing","Employee1");


        }


    }


}
