package com.ap.scrumboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor mainCursor;
    private Cursor empCursor;
    private String[] mainTasks;
    private  ArrayAdapter<String> mainArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar =getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        Spinner oldMain = findViewById(R.id.old_main_task);
        Spinner oldEmp = findViewById(R.id.old_employee);


        dbHelper = new ScrumDatabaseHelper(this);
        try{

            db = dbHelper.getReadableDatabase();
            mainCursor = db.query(Contract.PARENT_TABLE,new String[]{Contract.ID,Contract.PARENT_TASK},null,null,null,null,null);
            empCursor = db.query(Contract.EMPLOYEE_TABLE,new String[]{Contract.ID,Contract.EMPLOYEE},null,null,null,null,null);

            mainTasks = getArray(mainCursor);
            String[] employees = getArray(empCursor);

            mainArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mainTasks);
            ArrayAdapter<String> empArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,employees);


            mainArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            empArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            oldMain.setAdapter(mainArrayAdapter);
            oldEmp.setAdapter(empArrayAdapter);


        }catch (SQLException e){
            Toast.makeText(this,"Database unavailable",Toast.LENGTH_LONG).show();
        }


    }

    public void onClickAddMain(View view){

        EditText newMain = findViewById(R.id.new_main_task);
        String main = newMain.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Contract.PARENT_TASK,main);

        dbHelper = new ScrumDatabaseHelper(this);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(Contract.PARENT_TABLE,null,cv);
            swapMainCursor();
        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    public void onClickAddSub(View view){
        Spinner oldMain = findViewById(R.id.old_main_task);
        EditText newSub = findViewById(R.id.new_sub_task);
        Spinner status = findViewById(R.id.status);
        Spinner oldEmployee = findViewById(R.id.old_employee);

        String main = oldMain.getSelectedItem().toString();
        String sub = newSub.getText().toString();
        String stat = status.getSelectedItem().toString();
        String employee = oldEmployee.getSelectedItem().toString();

        ContentValues cv = new ContentValues();
        cv.put(Contract.PARENT,main);
        cv.put(Contract.SUB_TASK,sub);
        cv.put(Contract.STATUS,stat);
        cv.put(Contract.EMPLOYEE,employee);

        dbHelper = new ScrumDatabaseHelper(this);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(Contract.SUB_TABLE,null,cv);
            db.close();

        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    public void onClickAddEmployee(View view){
        EditText employee = findViewById(R.id.new_employee);
        String emp = employee.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Contract.EMPLOYEE,emp);

        dbHelper = new ScrumDatabaseHelper(this);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(Contract.EMPLOYEE_TABLE,null,cv);
            db.close();

        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    public String[] getArray(Cursor cursor){
        cursor.moveToFirst();
        ArrayList<String> tasks = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            tasks.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return tasks.toArray(new String[tasks.size()]);
    }

    public void swapMainCursor(){
        Log.v("swap cursor","Swapped curosr");
        if(mainCursor!=null){
            mainCursor.close();
        }
        Cursor newCursor = db.query(Contract.PARENT_TABLE,new String[]{Contract.ID,Contract.PARENT_TASK},null,null,null,null,null);
        mainCursor = newCursor;
        mainTasks = getArray(mainCursor);

        if(newCursor!=null){
            mainArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mainCursor.close();
        empCursor.close();
        db.close();
    }
}