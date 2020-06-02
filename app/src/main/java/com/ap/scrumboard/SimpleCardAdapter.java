package com.ap.scrumboard;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleCardAdapter extends RecyclerView.Adapter<SimpleCardAdapter.ViewHolder> {


        private Cursor cursor;


        public SimpleCardAdapter( Cursor cursor){
            this.cursor= cursor;
        }
/*
    interface Listener {
        void onClick(int position);
    }
    public void setListner(Listener listner){
        this.listner = listner;
    }

 */

        @Override
        public int getItemCount(){
            return cursor.getCount();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            private CardView cv;

            public ViewHolder(CardView v){
                super(v);
                cv=v;
            }
        }

        @Override
        public SimpleCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_card,parent,false);
            return new ViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){

            CardView cardView = holder.cv;
            TextView title = cardView.findViewById(R.id.main_title);
            String string = cursor.getString(0);
            title.setText(string);

        /*cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null){
                    listner.onClick(position);
                }
            }
        });

         */
        }
    }


