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

import com.ale.gttt.Interfaces.ISUser;
import com.ale.gttt.entities.User;
import com.ale.gttt.io.ServiceBA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {
private Button btnaceptarregistro, btncancelarregistro;
MediaPlayer clic, add;
private EditText etnick, etpass1, etpass2, etemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        Init();
    }
    private void Init(){
        clic=MediaPlayer.create(this, R.raw.clic);
        add=MediaPlayer.create(this, R.raw.add);
        btncancelarregistro=findViewById(R.id.btncancelarregistro);
        btnaceptarregistro=findViewById(R.id.btnaceptarregistro);
        btnaceptarregistro.setOnClickListener(this);
        btncancelarregistro.setOnClickListener(this);
        etemail=findViewById(R.id.etmailregistro);
        etnick=findViewById(R.id.etnickregistro);
        etpass1=findViewById(R.id.etpass1registro);
        etpass2=findViewById(R.id.etpass2registro);


    }
    public void onClick(View v){
        if (v==btnaceptarregistro){
            clic.start();
            SaveUser();
        }
        if (v==btncancelarregistro){
        ClearTask();
        }
    }

    private void SaveUser() {
        User u= GetNew();
        Call<Void> call = ServiceBA.getInstance().createService(ISUser.class).Create(u);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Realizado", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Sin recursos", Toast.LENGTH_LONG).show();
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error del servidor", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("newuser", "error: "+t.getMessage());
                Toast.makeText(getApplicationContext(), "Fallo en la conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private User GetNew() {
        String name, email, pass1, pass2;
        name= String.valueOf(etnick.getText());
        email= String.valueOf(etemail.getText());
        pass1= String.valueOf(etpass1.getText());
        pass2= String.valueOf(etpass2.getText());

        User u= null;
        if (!pass1.equals(pass2)){
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();

        }else {
            u=new User();
            u.setNick(name);
            u.setEmail(email);
            u.setPassword(pass1);


        }

        return u;
    }
    private void ClearTask(){
        Intent i= new Intent(getApplicationContext(), Main2Activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
