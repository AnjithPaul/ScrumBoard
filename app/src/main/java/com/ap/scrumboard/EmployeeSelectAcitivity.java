package com.ap.scrumboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployeeSelectAcitivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor empCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_select_acitivity);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar =getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        dbHelper = new ScrumDatabaseHelper(this);
        try{

            db = dbHelper.getReadableDatabase();
            empCursor = db.query(Contract.EMPLOYEE_TABLE,new String[]{Contract.ID,Contract.EMPLOYEE},null,null,null,null,null);
            String[] employees = getArray(empCursor);
            ArrayAdapter<String> empArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,employees);
            empArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinner = findViewById(R.id.spinner);
            spinner.setAdapter(empArrayAdapter);


        }catch (SQLException e){
            Toast.makeText(this,"Database unavailable",Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSelect(View view){
        Spinner spinner = findViewById(R.id.spinner);
        String emp = spinner.getSelectedItem().toString();
        Log.v("Employ select",emp);
        Intent intent = new Intent(this,EmployeeActivity.class);
        intent.putExtra(EmployeeActivity.EMPLOYEE,emp);
        startActivity(intent);
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
}