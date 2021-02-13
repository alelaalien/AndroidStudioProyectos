package com.ale.gttt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{
private Button btnaddst,btnguardardocenteedicion, btncancelardocenteedicion;
private EditText etnuevodocentenombre, etnuevodocentealias, etnuevodocentetelefono, etnuevodocenteemail, etnuevodocenteapellido, etast;
private RecyclerView tvdicta;
MediaPlayer pop, borrar, clic;

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
       // tvdicta= findViewById(R.id.rvdicta);

        pop= MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);

        Config();
    }

    private void Config() {
        btnguardardocenteedicion.setOnClickListener(this);
        btncancelardocenteedicion.setOnClickListener(this);
        btnaddst.setOnClickListener(this);

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

    public void Add(){

        String name, email, surname, nick, subjets,  notes;
        int celphone;
        name= String.valueOf(etnuevodocentenombre.getText());
        email= String.valueOf(etnuevodocenteemail.getText());
        surname= String.valueOf(etnuevodocenteapellido.getText());
        nick= String.valueOf(etnuevodocentealias.getText());
        subjets= String.valueOf(etast.getText());
        celphone=Integer.parseInt(String.valueOf(etnuevodocentetelefono.getText()));
        notes= "";


            Teacher t = new Teacher();
            t.setEmail(email);
            t.setCelephone(celphone);
            t.setName(name);
            t.setNick(nick);
            t.setNotes(notes);
            t.setSubjets(subjets);
            t.setSurname(surname);



            Call<Void> call= ServiceBA
                    .getInstance()
                    .createService(ISTeacher.class).Create(t);


            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(getApplicationContext(), MenuTabActivity.class);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

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

