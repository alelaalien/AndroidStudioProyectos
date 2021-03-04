package com.ale.gttt.Tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ale.gttt.MenuTabActivity;
import com.ale.gttt.R;

import java.util.ArrayList;

public class Utilities   {

    public Utilities( ) {

    }
    public String ToUpper(String string){

        String str = string;
        if (string.length()>0){

            String firstLtr = str.substring(0, 1);
            String restLtrs = str.substring(1, str.length());

            firstLtr = firstLtr.toUpperCase();
            str = firstLtr + restLtrs;

        }else{
            str="";
        }
        return str;
    }

    public void ClearTask(Context c){
        Intent i= new Intent(c, MenuTabActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }


}
