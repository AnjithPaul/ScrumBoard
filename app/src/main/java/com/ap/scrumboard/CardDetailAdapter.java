package com.ap.scrumboard;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CardDetailAdapter extends RecyclerView.Adapter<CardDetailAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public CardDetailAdapter(Context context ,Cursor cursor){
        this.context = context;
        this.cursor = cursor;

    }


    @Override
    public CardDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail,parent,false);
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

        int id = cursor.getInt(cursor.getColumnIndex(Contract.ID));
        String subTask = cursor.getString(cursor.getColumnIndex(Contract.SUB_TASK));
        String employee = cursor.getString(cursor.getColumnIndex(Contract.EMPLOYEE));

        TextView sub = cardView.findViewById(R.id.sub_task);
        TextView emp = cardView.findViewById(R.id.employee);

        sub.setText(subTask);
        emp.setText(employee);

        holder.itemView.setTag(id);

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
