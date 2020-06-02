package com.ap.scrumboard;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;
    //private Listener listner;

    public RecyclerCardAdapter(Context context ,Cursor cursor){
        this.context = context;
        this.cursor = cursor;

    }

    @Override
    public RecyclerCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list,parent,false);
        return new RecyclerCardAdapter.ViewHolder(cardView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView c;

        public ViewHolder(CardView v){
            super(v);
            c=v;
        }
    }

    @Override
    public int getItemCount(){

        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(RecyclerCardAdapter.ViewHolder holder, final int position){
        if(!cursor.moveToPosition(position)){
            return;
        }

        CardView cardView = holder.c;

        String mainTask = cursor.getString(cursor.getColumnIndex(Contract.PARENT_TASK));
        TextView textView = cardView.findViewById(R.id.main_title);
        textView.setText(mainTask);
        SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(context);
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor subCursor = db.rawQuery("SELECT SUBTASK FROM SUBTABLE WHERE PARENT =? AND STATUS =0",new String[]{mainTask});


            SimpleCardAdapter adapter = new SimpleCardAdapter(subCursor);
            RecyclerView recyclerView =cardView.findViewById(R.id.recycle_in_card);
            recyclerView.setAdapter(adapter);

            subCursor.close();
        }catch (SQLException e){
            Toast.makeText(context,"Database subCursor in adapter",Toast.LENGTH_LONG).show();
        }



        TextView mainTitle = cardView.findViewById(R.id.main_title);

        mainTitle.setText(mainTask);

        holder.itemView.setTag(mainTask);

    }

    public String[] getArray(Cursor cursor){
        cursor.moveToFirst();
        ArrayList<String> tasks = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            tasks.add(cursor.getString(cursor.getColumnIndex(Contract.SUB_TASK)));
            cursor.moveToNext();
        }
        cursor.close();
        return tasks.toArray(new String[tasks.size()]);
    }



}



