package com.ale.gttt.Tools;

import android.content.Context;
import android.content.Intent;

import com.ale.gttt.MenuTabActivity;

public class Utilities {

    public Utilities( ) {

    }
    public String ToUpper(String string){
        String str = string;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());

        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        return str;
    }

    public void ClearTask(Context c){
        Intent i= new Intent(c, MenuTabActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }

}
