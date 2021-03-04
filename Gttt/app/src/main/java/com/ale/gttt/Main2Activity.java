package com.ale.gttt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.ale.gttt.Interfaces.ISSession;
import com.ale.gttt.Interfaces.ISUser;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.Login;
import com.ale.gttt.entities.Token;
import com.ale.gttt.entities.User;
import com.ale.gttt.io.ServiceBA;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main2Activity extends AppCompatActivity {
private Button  btnaceptaringreso, btncrearcuenta;
MediaPlayer mp, intro;
private int id;

private EditText etnickingreso, etpassingreso;
private String token;
private User u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intro=MediaPlayer.create(this, R.raw.intro);
        intro.start();
        OnStart();
        Init();
    }
    public void iniciar(int q)  {
        if (q==0){
            Intent i= new Intent(this, MenuTabActivity.class);
            startActivity(i);
            getToken();
        }
        if(q==1){
            Intent i= new Intent(this, SingUpActivity.class);


            startActivity(i);
        }

    }

    private void getToken() {
        ArrayList<Token> array= new ArrayList<>();
        String email= etnickingreso.getText().toString();
        String pass=etpassingreso.getText().toString();
        Login l=new Login();
        l.setEmail(email);
        l.setPassword(pass);


        Call<Token> call= ServiceBA.getInstance().createService(ISSession.class).Login(l);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code()==200){

                    token= response.body().getToken();
                    id=response.body().getId();

                    SharedPreferenceManager.getInstance(getApplicationContext()).SetToken("Bearer "+token);
                    Login(id, token);

                }else  if (response.code()==401){
                    Toast.makeText(getApplicationContext(), "No autorizado " , Toast.LENGTH_SHORT ).show();
                    Log.d("no " ,  "401");
                }else  if (response.code()==500){
                    Toast.makeText(getApplicationContext(), " Error 500" , Toast.LENGTH_SHORT ).show();
                    Log.d("no " ,  "500");
                }

            }

            @Override
            public void onFailure(Call<Token>call, Throwable t) {
                Log.d("fail " ,  t.getMessage());
            }
        });
    }

    private void Init(){
        mp= MediaPlayer.create(this, R.raw.clic);
        btnaceptaringreso=findViewById(R.id.btnaceptaringreso);
        btncrearcuenta=findViewById(R.id.btncrearcuenta);
        etnickingreso=findViewById(R.id.etemailingreso2);
        etpassingreso=findViewById(R.id.etpassingreso2);
    Config();
    }

    private void Config(){
        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                startActivity(new Intent(getApplicationContext(), SingUpActivity.class));
       }
        });
        btnaceptaringreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                if (etnickingreso.getText().toString().trim().length() > 0&&etpassingreso.getText().toString().trim().length() >0){

                    getToken();
                }else{
                    Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                }
         }
        });
    }

    private void Login(int id, String t){

    u=new User( );


        Call<User> call = ServiceBA.getInstance().createService(ISUser.class).GetById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.code()==200){
                     u=response.body();


                    SharedPreferenceManager.getInstance(getApplicationContext())
                            .SaveUser(u); Log.d("token",  u.getToken()+"");
                    OnStart();
                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Usuario no encontrado");
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error: ", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Error 500: "+response.code());
                }else {
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Error : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("inicio", "Error desconocido: "+t.getMessage());
            }
        }); }


    protected void OnStart(){
        super.onStart();
        if (SharedPreferenceManager.getInstance(this).IsLoged()){
            startActivity(new Intent(getApplicationContext(), MenuTabActivity.class));
        }
    }
}


