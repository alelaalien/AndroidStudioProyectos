package com.ale.gttt;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{
private Button btnaddst,btnguardardocenteedicion, btncancelardocenteedicion;
private EditText etnuevodocentenombre, etnuevodocentealias, etnuevodocentetelefono, etnuevodocenteemail, etnuevodocenteapellido, etast;
private RecyclerView tvdicta;
MediaPlayer pop, borrar, clic;
private ArrayList<String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        Init();
    }
    private  void  Init(){
        btnaddst= findViewById(R.id.btnaddst);

        btncancelardocenteedicion= findViewById(R.id.btncancelardocenteedicion);
        btnguardardocenteedicion= findViewById(R.id.btnguardardocenteedicion);
        etnuevodocentenombre= findViewById(R.id.etnuevodocentenombre);
        etnuevodocentealias= findViewById(R.id.etnuevodocentealias);
        etnuevodocentetelefono= findViewById(R.id.etnuevodocentetelefono);
        etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
        etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
        etnuevodocenteapellido= findViewById(R.id.etnuevodocenteapellido);
        etast= findViewById(R.id.etast);
        tvdicta= findViewById(R.id.rvdicta);

        pop= MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);
        Intent dataintent= getIntent();
        data=dataintent.getStringArrayListExtra("arraydata");
        Config();

    }



    private void Config() {
        btnguardardocenteedicion.setOnClickListener(this);
        btncancelardocenteedicion.setOnClickListener(this);
        btnaddst.setOnClickListener(this);

    }
    private void GetValues (){

        if (etnuevodocentenombre.getText().toString().trim().length() > 0||etnuevodocenteapellido.getText().toString().trim().length() > 0||etnuevodocentealias.getText().toString().trim().length() > 0){
            String value=btnguardardocenteedicion.getText().toString();
            Teacher teacher= new Teacher();
            String nameLower=etnuevodocentenombre.getText().toString();
            String name=ToUpper(nameLower);
            String surnameLower=etnuevodocenteapellido.getText().toString();
            String surname=ToUpper(surnameLower);
            String nickLower=etnuevodocentealias.getText().toString();
            String nick=ToUpper(nickLower);
           // String classes = new Gson().toJson(values);
            int id= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
            teacher.setIdUser(id);
            teacher.setName(name);
//            if (values!=null){
//                teacher.setSubjets(values);
//
//            }else{
//                teacher.setSubjets("");
//            }
//
//
//            if (value.equals("Cancelar")){
//                Save(teacher);
//            }else if (value.equals("Volver")) {
//
//                Edit(teacher);
//            }
        }else{
            Toast.makeText(getApplicationContext(), "Faltan campos por completar", Toast.LENGTH_LONG).show();
            etnuevodocentenombre.requestFocus();
        }
    }

    private void Save(Teacher teacher) {
    }

    private void Empty() {
        etnuevodocentenombre.setSelection(0);
        etnuevodocentealias.setText("");
        etnuevodocenteapellido.setText("");
        etnuevodocentetelefono.setText("");
        etnuevodocenteemail.setText("");
     //   tvdicta.setText("");

    }

    private void ClearTask(){
        Intent i= new Intent(getApplicationContext(), MenuTabActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    private String ToUpper(String string){
        String str = string;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());

        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        return str;
    }


    public void onClick(View v){
        if(v==btnaddst ){
            pop.start();
        }
        if (v==btnguardardocenteedicion){
            clic.start();
            Add();
        }
        if (v==btncancelardocenteedicion){
            borrar.start();
        }
    }

          
    public void Alert(){


        AlertDialog.Builder dialog= new AlertDialog.Builder(AddTeacherActivity.this);
        dialog.setMessage("¿Eliminar?").setCancelable(true)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id= Integer.parseInt(data.get(1));

                        try {
                            Delete(id);
                            ClearTask();

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

    private void Delete(int id) {
    }

    public void Add(){






//            Call<Void> call= ServiceBA
//                    .getInstance()
                 //   .createService(ISTeacher.class).Create(t);


//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
//                    Intent i= new Intent(getApplicationContext(), MenuTabActivity.class);
//                    startActivity(i);
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
    }

}

//    ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSpinner);
//        spinmateridocentes.setAdapter(adapter);
//                spinmateridocentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//@Override
//public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        ConsultaDocentes(spinmateridocentes.getSelectedItem().toString(), 0);
//        }
//
//@Override
//public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//        });


//      ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
//  spindia.setAdapter(adapter);



//    private void leerMaterias(){
//        materiasSpinner=new ArrayList<String>();
//        materiasSpinner.add("-Seleccionar-");
//        for(int i=0; i<listaMaterias.size(); i++){
//            materiasSpinner.add(listaMaterias.get(i).getNombreMateria());
//        }
//  ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSpinner);
// s.setAdapter(adapter);
//String json=  new Gson().fromJson(classes, new TypeToken<List<AuxiliarSch>>(){}.getType());

