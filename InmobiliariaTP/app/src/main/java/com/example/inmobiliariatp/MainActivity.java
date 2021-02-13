package com.example.inmobiliariatp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Pag2(View view){
        Intent i= new Intent(this, Pag2.class);
        startActivity(i);
    }

    public void Pag3(View view) {
        Intent i= new Intent(this, Pag3.class);
        startActivity(i);
    }
}
