package com.example.booklibraryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private static Context context;
    Activity activity;
    static Animation translate_anim;
    private ArrayList book_id, book_title, book_author, book_pages;



    CustomAdapter(Activity activity,
            Context context,
                  ArrayList book_id,
                  ArrayList book_title,
                  ArrayList book_author,
                  ArrayList book_pages ){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_author = book_author;
        this.book_title = book_title;
        this.book_pages = book_pages;


    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
       // holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position))); // transfers the information of the selected book to the new Activity
                activity.startActivityForResult(intent, 1); //use activity instead of context obhject to "refresh the page" and get the newest database info
                notifyItemChanged(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //book_id_txt = itemView.findViewById(R.id.medicine_id_txt);
            book_title_txt = itemView.findViewById(R.id.medicine_title_txt);
            book_author_txt = itemView.findViewById(R.id.medicine_subText_txt);
            book_pages_txt = itemView.findViewById(R.id.medicine_pills_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recycler View
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);


        }
    }

}


