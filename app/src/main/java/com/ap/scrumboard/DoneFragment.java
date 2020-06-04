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


public class DoneFragment extends Fragment {
    private SQLiteDatabase db;
    private CardListAdapter adapter;
    private int n;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView toDoRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_done,container,false);

        final SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(getActivity());
        try {
            db = dbHelper.getReadableDatabase();
            // Cursor subCursor = db.query(Contract.SUB_TABLE,new String[] {Contract.SUB_TASK,Contract.PARENT},Contract.STATUS+"=?",new String[]{"To Do"},Contract.PARENT,null,null);
            Cursor mainCursor = db.query(Contract.PARENT_TABLE,new String[]{Contract.PARENT_TASK},null,null,null,null,null);


            adapter = new CardListAdapter(getActivity(), mainCursor,"Done");
            toDoRecycler.setAdapter(adapter);

        }catch (SQLException e){
            Toast toast =Toast.makeText(getActivity(),"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        toDoRecycler.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((String) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(toDoRecycler);

        adapter.setListner(new CardListAdapter.Listener() {
            @Override
            public void onClick(String id) {
                Intent intent = new Intent(getActivity(),DetailTaskActivity.class);
                intent.putExtra(DetailTaskActivity.MAINTASK,id);
                getActivity().startActivity(intent);

            }
        });

        return toDoRecycler;
    }
    private void removeItem(String id){
        db.delete("PARENTTABLE","PARENTTASK =?",new String[]{id});
        adapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return db.query(Contract.PARENT_TABLE,new String[]{Contract.PARENT_TASK},null,null,null,null,null);
    }
}