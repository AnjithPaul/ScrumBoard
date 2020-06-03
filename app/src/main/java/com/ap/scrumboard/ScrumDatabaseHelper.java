package com.ap.scrumboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScrumDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "scrum";
    private static final int DB_VERSION = 21;

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
            db.execSQL(" CREATE TABLE " + Contract.PARENT_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.PARENT_TASK + " TEXT UNIQUE);");
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.SUB_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.SUB_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.SUB_TASK + " TEXT,"+ Contract.PARENT+" TEXT, STATUS INTEGER,"+Contract.EMPLOYEE +"TEXT);");

        }
        if(oldVersion < 21){
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.EMPLOYEE_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.EMPLOYEE_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.EMPLOYEE + " TEXT);");
            db.execSQL(" DROP TABLE IF EXISTS "+Contract.SUB_TABLE);
            db.execSQL(" CREATE TABLE " + Contract.SUB_TABLE + "("+ Contract.ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.SUB_TASK + " TEXT,"+ Contract.PARENT+" TEXT, STATUS TEXT,EMPLOYEE TEXT);");

            //Demo data set for the Scrum Board application


            insertMain(db,"Main task 1");
            insertMain(db,"Main task 2");
            insertMain(db,"Main task 3");
            insertMain(db,"Main task 4");
            insertMain(db,"Main task 5");
            insertEmp(db,"Employee1");
            insertEmp(db,"Employee2");
            insertEmp(db,"Employee3");
            insertEmp(db,"Employee4");
            insertEmp(db,"Employee5");
            insertSub(db,"Sub task 1","Main task 1","To Do","Employee4");
            insertSub(db,"Sub task 2","Main task 1","To Do","Employee5");
            insertSub(db,"Sub task 3","Main task 1","Doing","Employee1");
            insertSub(db,"Sub task 4","Main task 1","Doing","Employee2");
            insertSub(db,"Sub task 5","Main task 1","Doing","Employee3");
            insertSub(db,"Sub task 6","Main task 1","Done","Employee5");
            insertSub(db,"Sub task 7","Main task 1","Done","Employee4");
            insertSub(db,"Sub task 8","Main task 1","To Do","Employee5");
            insertSub(db,"Sub task 9","Main task 1","To Do","Employee2");
            insertSub(db,"Sub task 10","Main task 1","Doing","Employee3");
            insertSub(db,"Sub task 11","Main task 1","Doing","Employee3");
            insertSub(db,"Sub task 12","Main task 1","Doing","Employee1");
            insertSub(db,"Sub task 13","Main task 1","Done","Employee1");
            insertSub(db,"Sub task 14","Main task 1","Done","Employee2");
            insertSub(db,"Sub task 15","Main task 1","To Do","Employee1");
            insertSub(db,"Sub task 16","Main task 1","To Do","Employee4");
            insertSub(db,"Sub task 17","Main task 1","Doing","Employee3");
            insertSub(db,"Sub task 18","Main task 1","Doing","Employee4");
            insertSub(db,"Sub task 19","Main task 1","Doing","Employee5");
            insertSub(db,"Sub task 20","Main task 1","Done","Employee4");
            insertSub(db,"Sub task 21","Main task 2","To Do","Employee1");
            insertSub(db,"Sub task 22","Main task 2","To Do","Employee5");
            insertSub(db,"Sub task 23","Main task 2","Doing","Employee3");
            insertSub(db,"Sub task 24","Main task 2","Doing","Employee3");
            insertSub(db,"Sub task 25","Main task 2","Doing","Employee5");
            insertSub(db,"Sub task 26","Main task 2","Done","Employee1");
            insertSub(db,"Sub task 27","Main task 2","Done","Employee2");
            insertSub(db,"Sub task 28","Main task 2","To Do","Employee1");
            insertSub(db,"Sub task 29","Main task 2","To Do","Employee2");
            insertSub(db,"Sub task 30","Main task 2","Doing","Employee3");
            insertSub(db,"Sub task 31","Main task 2","Doing","Employee1");
            insertSub(db,"Sub task 32","Main task 2","Doing","Employee5");
            insertSub(db,"Sub task 33","Main task 2","Done","Employee1");
            insertSub(db,"Sub task 34","Main task 2","Done","Employee2");
            insertSub(db,"Sub task 35","Main task 2","To Do","Employee1");
            insertSub(db,"Sub task 36","Main task 2","To Do","Employee2");
            insertSub(db,"Sub task 37","Main task 2","Doing","Employee3");
            insertSub(db,"Sub task 38","Main task 2","Doing","Employee4");
            insertSub(db,"Sub task 39","Main task 2","Doing","Employee1");
            insertSub(db,"Sub task 40","Main task 2","Done","Employee1");
            insertSub(db,"Sub task 41","Main task 3","To Do","Employee1");
            insertSub(db,"Sub task 42","Main task 3","To Do","Employee2");
            insertSub(db,"Sub task 43","Main task 3","Doing","Employee3");
            insertSub(db,"Sub task 44","Main task 3","Doing","Employee4");
            insertSub(db,"Sub task 45","Main task 3","Doing","Employee1");
            insertSub(db,"Sub task 46","Main task 3","Done","Employee1");
            insertSub(db,"Sub task 47","Main task 3","Done","Employee2");
            insertSub(db,"Sub task 48","Main task 3","To Do","Employee1");
            insertSub(db,"Sub task 49","Main task 3","To Do","Employee2");
            insertSub(db,"Sub task 50","Main task 3","Doing","Employee3");
            insertSub(db,"Sub task 51","Main task 3","Doing","Employee4");
            insertSub(db,"Sub task 52","Main task 3","Doing","Employee2");
            insertSub(db,"Sub task 53","Main task 3","Done","Employee1");
            insertSub(db,"Sub task 54","Main task 3","Done","Employee2");
            insertSub(db,"Sub task 55","Main task 3","To Do","Employee1");
            insertSub(db,"Sub task 56","Main task 3","To Do","Employee2");
            insertSub(db,"Sub task 57","Main task 3","Doing","Employee3");
            insertSub(db,"Sub task 58","Main task 3","Doing","Employee2");
            insertSub(db,"Sub task 59","Main task 3","Doing","Employee5");
            insertSub(db,"Sub task 60","Main task 3","Done","Employee1");
            insertSub(db,"Sub task 61","Main task 4","To Do","Employee4");
            insertSub(db,"Sub task 62","Main task 4","To Do","Employee5");
            insertSub(db,"Sub task 63","Main task 4","Doing","Employee4");
            insertSub(db,"Sub task 64","Main task 4","Doing","Employee4");
            insertSub(db,"Sub task 65","Main task 4","Doing","Employee5");
            insertSub(db,"Sub task 66","Main task 4","Done","Employee4");
            insertSub(db,"Sub task 67","Main task 4","Done","Employee5");
            insertSub(db,"Sub task 68","Main task 4","To Do","Employee1");
            insertSub(db,"Sub task 69","Main task 4","To Do","Employee5");
            insertSub(db,"Sub task 70","Main task 4","Doing","Employee4");
            insertSub(db,"Sub task 71","Main task 4","Doing","Employee4");
            insertSub(db,"Sub task 72","Main task 4","Doing","Employee5");
            insertSub(db,"Sub task 73","Main task 4","Done","Employee1");
            insertSub(db,"Sub task 74","Main task 4","Done","Employee2");
            insertSub(db,"Sub task 75","Main task 4","To Do","Employee1");
            insertSub(db,"Sub task 76","Main task 4","To Do","Employee2");
            insertSub(db,"Sub task 77","Main task 4","Doing","Employee3");
            insertSub(db,"Sub task 78","Main task 4","Doing","Employee4");
            insertSub(db,"Sub task 79","Main task 4","Doing","Employee5");
            insertSub(db,"Sub task 80","Main task 4","Done","Employee1");
            insertSub(db,"Sub task 81","Main task 5","To Do","Employee1");
            insertSub(db,"Sub task 82","Main task 5","To Do","Employee2");
            insertSub(db,"Sub task 83","Main task 5","Doing","Employee3");
            insertSub(db,"Sub task 84","Main task 5","Doing","Employee4");
            insertSub(db,"Sub task 85","Main task 5","Doing","Employee5");
            insertSub(db,"Sub task 86","Main task 5","Done","Employee1");
            insertSub(db,"Sub task 87","Main task 5","Done","Employee2");
            insertSub(db,"Sub task 88","Main task 5","To Do","Employee1");
            insertSub(db,"Sub task 89","Main task 5","To Do","Employee2");
            insertSub(db,"Sub task 90","Main task 5","Doing","Employee3");
            insertSub(db,"Sub task 91","Main task 5","Doing","Employee4");
            insertSub(db,"Sub task 92","Main task 5","Doing","Employee5");
            insertSub(db,"Sub task 93","Main task 5","Done","Employee1");
            insertSub(db,"Sub task 94","Main task 5","Done","Employee2");
            insertSub(db,"Sub task 95","Main task 5","To Do","Employee1");
            insertSub(db,"Sub task 96","Main task 5","To Do","Employee2");
            insertSub(db,"Sub task 97","Main task 5","Doing","Employee3");
            insertSub(db,"Sub task 98","Main task 5","Doing","Employee4");
            insertSub(db,"Sub task 99","Main task 5","Doing","Employee5");
            insertSub(db,"Sub task 100","Main task 5","Done","Employee1");
            insertSub(db,"Sub task 101","Main task 5","Done","Employee2");

        }



    }


}
