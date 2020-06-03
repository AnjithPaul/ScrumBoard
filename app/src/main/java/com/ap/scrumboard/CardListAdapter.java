package com.ap.scrumboard;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;
    private Listener listner;
    private String status;

    public CardListAdapter(Context context ,Cursor cursor,String status){
        this.context = context;
        this.cursor = cursor;
        this.status= status;

    }
    interface Listener {
        void onClick(String maintask);
    }

    public void setListner(Listener listner) {
        this.listner = listner;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list,parent,false);
        return new ViewHolder(cardView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cv;

        public ViewHolder(CardView v){
            super(v);
            cv=v;
        }
    }

    @Override
    public int getItemCount(){

        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position){
        if(!cursor.moveToPosition(position)){
            return;
        }

        CardView cardView = holder.cv;

        final String mainTask = cursor.getString(cursor.getColumnIndex(Contract.PARENT_TASK));
        SQLiteOpenHelper dbHelper = new ScrumDatabaseHelper(context);
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor subCursor = db.rawQuery("SELECT SUBTASK FROM SUBTABLE WHERE PARENT =? AND STATUS =?",new String[]{mainTask, status});


            String[] subTasks = getArray(subCursor);
            ArrayAdapter<String > listAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,subTasks);

            ListView listView = cardView.findViewById(R.id.list_in_card);
            listView.setAdapter(listAdapter);


            subCursor.close();
        }catch (SQLException e){
            Toast.makeText(context,"Database subCursor in adapter",Toast.LENGTH_LONG).show();
        }



        TextView mainTitle = cardView.findViewById(R.id.main_title);

        mainTitle.setText(mainTask);

        holder.itemView.setTag(mainTask);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null){
                    listner.onClick(mainTask);
                }
            }
        });


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
    public void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor = newCursor;

        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }



}
