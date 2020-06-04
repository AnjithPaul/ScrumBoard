package com.ap.scrumboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;


public class MainDoneFragment extends Fragment {
    private SQLiteDatabase db;
    private CardListAdaptertwo adapter;
    private int n;
    private String emp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView toDoRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_main_done,container,false);

        final SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(getActivity());
        try {
            String s = this.emp;
            db = dbHelper.getReadableDatabase();
            Cursor mainCursor = db.rawQuery("SELECT DISTINCT PARENT FROM SUBTABLE WHERE  STATUS =? AND EMPLOYEE =?",new String[]{"Done", emp});
            adapter = new CardListAdaptertwo(getActivity(), mainCursor,"Done",s);
            toDoRecycler.setAdapter(adapter);

        }catch (SQLException e){
            Toast toast =Toast.makeText(getActivity(),"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        toDoRecycler.setLayoutManager(layoutManager);

        return toDoRecycler;
    }

    public void setEmp(String string){
        this.emp= string;
    }
}