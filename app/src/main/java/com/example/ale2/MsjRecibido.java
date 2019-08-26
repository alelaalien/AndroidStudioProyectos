package com.example.ale2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MsjRecibido extends BroadcastReceiver {
    public MsjRecibido() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Mensaje Recibido", Toast.LENGTH_SHORT).show();

    }
}
