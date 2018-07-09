package com.example.arturkasymov.application_b;

import android.annotation.SuppressLint;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

@SuppressLint("NewApi")
class MyObserver extends ContentObserver {

    public MyObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        try {
            MainActivity.massage();
        }catch (Exception ex){
        //if secondFragmet is already closed
    }


    }
}