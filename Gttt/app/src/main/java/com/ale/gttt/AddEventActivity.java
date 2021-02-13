package com.ale.gttt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddEventActivity extends AppCompatActivity  implements View.OnClickListener  {
private Button btnhora, btnfecha, btncancelarnuevoevento, btnaceptarnuevoevento, togglenuevoevento;
MediaPlayer pop, borrar, clic;
private RadioButton rbaltanuevoevento, rbmedianuevoevento, rbbajanuevoevento;
private EditText ettemarionuevoevento, etfecha, ethora, etdescripcionnuevoevento;
private Spinner spinnuevoevento;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Init();
    }
    private  void  Init(){
        btnaceptarnuevoevento= findViewById(R.id.btnguardarnuevoevento);
        btnfecha= findViewById(R.id.btnfecha);
        btncancelarnuevoevento= findViewById(R.id.btncancelarnuevoevento);
        btnhora= findViewById(R.id.btnhora);
        togglenuevoevento= findViewById(R.id.togglenuevoevento);
        rbaltanuevoevento=findViewById(R.id.rbaltanuevoevento);
        rbbajanuevoevento=findViewById(R.id.rbbajanuevoevento);
        ettemarionuevoevento=findViewById(R.id.ettemarionuevoevento);
        etfecha=findViewById(R.id.etfecha);
        ethora=findViewById(R.id.ethora);
        etdescripcionnuevoevento=findViewById(R.id.etdescripcionnuevoevento);
        rbmedianuevoevento=findViewById(R.id.rbmedianuevoevento);
        spinnuevoevento=findViewById(R.id.spinnuevoevento);

        Config();
    }
    private void Config(){
        pop=MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);
        btnaceptarnuevoevento.setOnClickListener(this);
        btnfecha.setOnClickListener(this);
        btnhora.setOnClickListener(this);
        btncancelarnuevoevento.setOnClickListener(this);
    }

 public void onClick(View v){
        if(v==btnfecha||v==btnhora){
            pop.start();
        }
        if (v==btnaceptarnuevoevento){
            clic.start();
            Add();
        }
        if (v==btncancelarnuevoevento){
            borrar.start();
            Intent i= new Intent(this, MenuTabActivity.class);
            startActivity(i);
        }
 }


        public void Add(){

        }



}
