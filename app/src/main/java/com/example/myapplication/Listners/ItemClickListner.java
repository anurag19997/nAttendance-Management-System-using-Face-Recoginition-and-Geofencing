package com.example.myapplication.Listners;

import android.view.View;

public interface ItemClickListner extends View.OnClickListener {
    void onCLick(View view, int position);
     void onLongClick(View view, int position);
}
