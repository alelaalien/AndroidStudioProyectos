package com.example.ale2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
private MsjRecibido msjRecibido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
     this.msjRecibido= new MsjRecibido();
     registerReceiver(this.msjRecibido, new IntentFilter("android.provide.Telephony.SMS_RECEIVED"));

        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(msjRecibido);
        super.onPause();
    }
}
