package com.example.inmobiliariatp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Pag2 extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag2);
        email=(EditText) findViewById(R.id.etemail_ingreso);
        pass=(EditText) findViewById(R.id.etpass_ingreso);
        aceptar=(Button) findViewById(R.id.btnlogin);
    }
    public void AMenu(View view){
        Intent i= new Intent(this, MenuLateral.class);
        startActivity(i);
    }
}
