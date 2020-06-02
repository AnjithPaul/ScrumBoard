package com.ap.scrumboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainTaskActivity extends AppCompatActivity {

    public static final String MAIN = "maintask";
    private int id;
    Cursor todoCursor;
    Cursor doingCursor;
    Cursor doneCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar =getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);


         id= (Integer)getIntent().getExtras().get(MAIN);

        SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(this);
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();
             todoCursor = db.rawQuery("SELECT SUBTASK FROM SUBTABLE WHERE PARENT =(SELECT PARENTTASK FROM PARENTTABLE WHERE _id=?) AND STATUS =?",new String[]{String.valueOf(id),"To Do"});
             doingCursor = db.rawQuery("SELECT SUBTASK FROM SUBTABLE WHERE PARENT =(SELECT PARENTTASK FROM PARENTTABLE WHERE _id=?) AND STATUS =?",new String[]{String.valueOf(id),"Doing"});
             doneCursor = db.rawQuery("SELECT SUBTASK FROM SUBTABLE WHERE PARENT =(SELECT PARENTTASK FROM PARENTTABLE WHERE _id=?) AND STATUS =?",new String[]{String.valueOf(id),"Done"});

        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }

        String[] todoA = getArray(todoCursor);
        Log.v("todoA",todoA[0]);
        String[] doingA = getArray(doingCursor);
        String[] doneA = getArray(doneCursor);

        ArrayAdapter<String> todoAA = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoA);
        ArrayAdapter<String> doingAA = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,doingA);
        ArrayAdapter<String> doneAA = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,doneA);


        ListView todo =(ListView) findViewById(R.id.todolist);
        ListView doing = findViewById(R.id.doinglist);
        ListView done = findViewById(R.id.donelist);

        //todo.setAdapter(todoAA);
       // doing.setAdapter(doingAA);
        //done.setAdapter(doneAA);

    }
    public String[] getArray(Cursor cursor){
        cursor.moveToFirst();
        ArrayList<String> tasks = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            tasks.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return tasks.toArray(new String[tasks.size()]);
    }





}

