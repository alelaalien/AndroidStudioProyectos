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

import com.ale.gttt.Interfaces.ISSecurity;
import com.ale.gttt.Interfaces.ISSession;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.BT;
import com.ale.gttt.entities.User;
import com.ale.gttt.io.ServiceBA;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main2Activity extends AppCompatActivity {
private Button  btnaceptaringreso, btncrearcuenta;
MediaPlayer mp, intro;
private EditText etnickingreso, etpassingreso;


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
        String user= "user";
        String pass="10000.cskJ5tiHOasWJo5IfzIeOQ==.t0q9fEMkNltHY5r6JFSlbbzb+yh5OeHdf0k4TLLJq4o=";
        BT bt= new BT(user, pass);

        Call<List<String>> call= ServiceBA.getInstance().createService(ISSecurity.class).getToken(bt);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Toast.makeText(getApplicationContext(), "Validado", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al validar", Toast.LENGTH_SHORT ).show();
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
    Login();
}else{
    Toast.makeText(getApplicationContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
}



            }
        });
    }

    private void Login(){
        String nick=String.valueOf(etnickingreso.getText());
        String pass=String.valueOf(etpassingreso.getText());
        User u=new User();

        u.setEmail(nick);
        u.setPassword(pass);
        Call<User> call = ServiceBA.getInstance().createService(ISSession.class).Login(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200){
                    Log.d("inicio", "Usuario encontrado");
                    SharedPreferenceManager.getInstance(getApplicationContext())
                            .SaveUser(response.body());
                    OnStart();
                 //   startActivity(new Intent(getApplicationContext(), MenuTabActivity.class));
                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Usuario no encontrado");
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error: ", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Error 500: "+response.code());
                }else {
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                    Log.d("inicio", "Error desconocido: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("inicio", "Error desconocvvvvvvvvvvvvvido: "+t.getMessage());
            }
        });

    }
    protected void OnStart(){
        super.onStart();
        if (SharedPreferenceManager.getInstance(this).IsLoged()){
            startActivity(new Intent(getApplicationContext(), MenuTabActivity.class));
        }
    }
}


