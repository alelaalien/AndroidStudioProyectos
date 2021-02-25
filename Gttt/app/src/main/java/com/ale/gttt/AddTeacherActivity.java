package com.ale.gttt;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.SubjetsAdapter;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnguardardocenteedicion, btncancelardocenteedicion, btndelete;
    private EditText etnuevodocentenombre, etnuevodocentealias, etnuevodocentetelefono, etnuevodocenteemail, etnuevodocenteapellido;
    MediaPlayer pop, borrar, clic;
    private TextView idediciondoc,   tvidsubt;
    private int idUser;
    private  Spinner spinn;
    private ArrayList<String> data;
    private ArrayList<Subjet> sublist;
    private GregorianCalendar gc;
    private Utilities u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        Init();
        Verifications();
    }

    private  void  Init(){
        u= new Utilities();
        btndelete=findViewById(R.id.btndeletet);
        tvidsubt=findViewById(R.id.tvidsubt);
        spinn=findViewById(R.id.spinnert);
        idUser=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        idediciondoc=findViewById(R.id.idediciondoc);
        btncancelardocenteedicion= findViewById(R.id.btncancelardocenteedicion);
        btnguardardocenteedicion= findViewById(R.id.btnguardardocenteedicion);
        etnuevodocentenombre= findViewById(R.id.etnuevodocentenombre);
        etnuevodocentealias= findViewById(R.id.etnuevodocentealias);
        etnuevodocentetelefono= findViewById(R.id.etnuevodocentetelefono);
        etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
        etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
        etnuevodocenteapellido= findViewById(R.id.etnuevodocenteapellido);
        sublist = new ArrayList<>();
        pop= MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);
        Intent dataintent= getIntent();
        data=dataintent.getStringArrayListExtra("datat");
        Config();
    }

    private void Verifications() {

        if (data!=null){
            SetTexts(1);
        }else{
            SetTexts(0);

        }

    }

    private void SetTexts(int i) {

        String title, btnEditSave, btnCancelReturn;
        String deleteTitle="Eliminar";

        if (i==0){
            title="Nuevo Docente";
            btnEditSave="Guardar";
            btnCancelReturn="Cancelar";
            btndelete.setVisibility(View.GONE);

        }else{

            title="Editar Docente";
            btnEditSave="Editar";
            btnCancelReturn="Volver";
            Enabled(false);
            etnuevodocentenombre.setText(data.get(1));
            etnuevodocenteemail.setText(data.get(6));
            etnuevodocentetelefono.setText(data.get(5));
            etnuevodocenteapellido.setText(data.get(2));
            etnuevodocentealias.setText(data.get(3));
            if (data.get(4).length()>0){
                GetSubjets(null, Integer.parseInt(data.get(4)));
            }
        }
        btndelete.setText(deleteTitle);
        idediciondoc.setText(title);
        btnguardardocenteedicion.setText(btnEditSave);
        btncancelardocenteedicion.setText(btnCancelReturn);
    }

    private void Config() {
        GetSubjets(null, 0);

        btnguardardocenteedicion.setOnClickListener(this);
        btncancelardocenteedicion.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        Calendar();
    }

    private void GetValues (){

        if (etnuevodocentenombre.getText().toString().trim().length() > 0){
            String value=btncancelardocenteedicion.getText().toString();
            Teacher teacher= new Teacher();
            String nameLower=etnuevodocentenombre.getText().toString();
            String name=u.ToUpper(nameLower);
            String surnameLower=etnuevodocenteapellido.getText().toString();
            String surname=u.ToUpper(surnameLower);
            String nickLower=etnuevodocentealias.getText().toString();
            String email=etnuevodocenteemail.getText().toString();
            String nick=u.ToUpper(nickLower);
            String celphone=etnuevodocentetelefono.getText().toString();
            int cel;
            if (celphone.length()>0){
                cel=Integer.parseInt(celphone);
            }else {
                cel=0;
            }
            int sub=0;
            try {
                sub=Integer.parseInt(tvidsubt.getText().toString());

            }catch (Exception e){
                Log.d("",e.getMessage());
            }
            teacher.setIdUser(idUser);
            teacher.setName(name);
            teacher.setSurname(surname);
            teacher.setNick(nick);
            teacher.setEmail(email);
            teacher.setCelphone(cel);
            teacher.setSubjet(sub);

            if (value.equals("Cancelar")){
                Add(teacher);
            }else if (value.equals("Volver")) {
                teacher.setId(Integer.parseInt(data.get(0)));
                Edit(teacher);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Faltan campos por completar", Toast.LENGTH_LONG).show();
            etnuevodocentenombre.requestFocus();
        }
    }

    private void Edit(Teacher teacher) {

        Call<Void> call= ServiceBA.getInstance().createService(ISTeacher.class).Update(teacher.getId(), teacher);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code=response.code();
                if (code==200){
                    Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_SHORT).show();
                    u.ClearTask(getApplicationContext());
                }else if(code==500){
                    Toast.makeText(getApplicationContext(), "Error del servidor. Reintentar más tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v){

        if (v==btnguardardocenteedicion){
            clic.start();
            clic.start();
            if (btncancelardocenteedicion.getText().toString().equals("Volver")){
                String action, acept, edit;
                action=btnguardardocenteedicion.getText().toString();
                acept="Aceptar";
                edit="Editar";



                if(action.equals("Editar")){
                    Enabled(true);
                    btnguardardocenteedicion.setText(acept);
                }else if(action.equals("Guardar")||action.equals("Aceptar")){
                    Enabled(false);
                    GetValues();
                    btnguardardocenteedicion.setText(edit);
                }
            }else {
                GetValues();
            }

        }
        if (v==btncancelardocenteedicion){
            borrar.start();
            u.ClearTask(getApplicationContext());
        }
        if (v==btndelete){
            Alert();
            }
        }

    private void Enabled(boolean b) {
        etnuevodocentealias.setEnabled(b);
        etnuevodocenteapellido.setEnabled(b);
        etnuevodocentetelefono.setEnabled(b);
        etnuevodocenteemail.setEnabled(b);
        etnuevodocentenombre.setEnabled(b);
        spinn.setEnabled(b);
    }

    public void onBackPressed( ){
        u.ClearTask(getApplicationContext());
    }

    public void Alert(){

        AlertDialog.Builder dialog= new AlertDialog.Builder(AddTeacherActivity.this);
        dialog.setMessage("¿Eliminar?").setCancelable(true)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id= Integer.parseInt(data.get(0));

                        try {
                            Delete(id);
                            u.ClearTask(getApplicationContext());

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog title= dialog.create();
        title.setTitle("Confirmar acción");

        title.show();
    }

    private void Calendar(){


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

    }

    private void GetSubjets(String name, int idSub){

        sublist= new ArrayList<>();

        Call<ArrayList<Subjet>> call ;
        if (name!=null){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, name, 0);
        }else if(idSub!=0){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetById(idSub);
        }else{
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, null, 0);
        }
        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

                if (response.code() == 200) {
                    sublist.addAll(response.body());


                    if (idSub!=0){//editar

                      //  SpinnConf(sublist, sublist.get(0).getName());

                    }
                    if (name==null){//todos

                        SpinnConf(sublist, null);
                    }else  if (name.length()>0){//obtener id- crear

                        int l=response.body().size();

                        if (l==0){
                            tvidsubt.setText(String.valueOf(l));
                        }else{
                            tvidsubt.setText(String.valueOf(sublist.get(0).getId()));
                        }
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("teacher materias", "Recursos no encontrados");
                } else if (response.code() == 500) {
                    Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Error 500", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {
            }
        });
    }

    private void Delete(int id) {
        Call<Void> call= ServiceBA.getInstance().createService(ISTeacher.class).Delete(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Hecho", Toast.LENGTH_LONG).show();
                }else  if (response.code()==404){
                    Toast.makeText(getApplicationContext(), "No encontrado", Toast.LENGTH_LONG).show();
                } else if (response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error del servidor. Reintentar más tarde", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Add(Teacher t){

     Call<Void> call= ServiceBA.getInstance().createService(ISTeacher.class).Create(t);
        call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int code=response.code();
                    Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                        u.ClearTask(getApplicationContext());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void SpinnConf(ArrayList<Subjet> sublist, String name) {

Toast.makeText(getApplicationContext(), ""+name, Toast.LENGTH_LONG).show();
        ArrayList<String> array= new ArrayList<>();
        array.add("Seleccionar");

        for (int i=0; i<sublist.size(); i++){

            array.add(sublist.get(i).getName());
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, array);
        spinn.setAdapter(adapter);
        spinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinn.getSelectedItemPosition()!=0){
                    GetSubjets( spinn.getSelectedItem().toString(), 0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (name!=null){

            int p= array.indexOf(name);
            int pos=p+1;
            spinn.setSelection(pos);
        }
    }


}
