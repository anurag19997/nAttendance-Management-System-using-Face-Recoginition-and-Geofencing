package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Listners.ItemClickListner;

public class MyRecyclerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView callerNameImageView, updateLeadsImageView;
    public TextView callerNameTextView, callingReminderTextview, userDetailsTextview;
    public ItemClickListner itemClickListner;


    public MyRecyclerViewholder(@NonNull View itemView, final ItemClickListner itemClickListner) {
        super(itemView);
        callerNameImageView = itemView.findViewById(R.id.caller_imageview);
        updateLeadsImageView = itemView.findViewById(R.id.update_icon_image);
        callerNameTextView = itemView.findViewById(R.id.caller_name_text);
        callingReminderTextview = itemView.findViewById(R.id.call_reminder_textview);
        userDetailsTextview = itemView.findViewById(R.id.user_details_calling_textview);
        this.itemClickListner = itemClickListner;

    }


    //currently this container is called
    public MyRecyclerViewholder(View itemView) {
        super(itemView);
        callerNameImageView = itemView.findViewById(R.id.caller_imageview);
        updateLeadsImageView = itemView.findViewById(R.id.update_icon_image);
        callerNameTextView = itemView.findViewById(R.id.caller_name_text);
        callingReminderTextview = itemView.findViewById(R.id.call_reminder_textview);
        userDetailsTextview = itemView.findViewById(R.id.user_details_calling_textview);
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onCLick(v, getAdapterPosition());
    }

//    public void setItemClickListner(View.OnClickListener itemClickListner) {
//        this.itemClickListner = itemClickListner;
//    }

//    @Override
//    public void onClick(View v) {
//        itemClickListner.onCLick(v, getAdapterPosition());
//
//    }



}
