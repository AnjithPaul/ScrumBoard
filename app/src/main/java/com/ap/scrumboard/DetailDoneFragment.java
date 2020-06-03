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


public class DetailDoneFragment extends Fragment {
    private SQLiteDatabase db;
    private CardDetailAdapter adapter;
    private String mainTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView toDoRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_detail_done,container,false);

        final SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(getActivity());
        try {
            String s = this.mainTask;
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM SUBTABLE WHERE PARENT =? AND STATUS =?",new String[]{s, "Done"});



            adapter = new CardDetailAdapter(getActivity(), cursor);
            toDoRecycler.setAdapter(adapter);

        }catch (SQLException e){
            Toast toast =Toast.makeText(getActivity(),"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        toDoRecycler.setLayoutManager(layoutManager);
       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(toDoRecycler);

        */

        return toDoRecycler;
    }
    private void removeItem(int id){
        db.delete("SUBTABLE","_id =?",new String[]{String.valueOf(id)});
        adapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return  db.rawQuery("SELECT * FROM SUBTABLE WHERE PARENT =? AND STATUS =?",new String[]{mainTask, "Doing"});
    }
    public void setMain(String string){
        this.mainTask= string;
    }
}