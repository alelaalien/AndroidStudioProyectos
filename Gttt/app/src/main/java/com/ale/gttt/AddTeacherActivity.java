package com.ale.gttt;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.SubjetsAdapter;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{
private Button btnguardardocenteedicion, btncancelardocenteedicion, btndelete;
private EditText etnuevodocentenombre, etnuevodocentealias, etnuevodocentetelefono, etnuevodocenteemail, etnuevodocenteapellido;
MediaPlayer pop, borrar, clic;
private TextView idediciondoc, etfill, tvidsubt;
private int idUser;
private ArrayList<String> data;
private ArrayList<Subjet> sublist;
private ArrayList<String> listSpin;
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
        etfill=findViewById(R.id.etfill);
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
            btnEditSave="Guardar Cambios";
            btnCancelReturn="Volver";
            etnuevodocentenombre.setText(data.get(1));
            etnuevodocenteemail.setText(data.get(6));
            etnuevodocentetelefono.setText(data.get(5));
            etnuevodocenteapellido.setText(data.get(2));
            etnuevodocentealias.setText(data.get(3));
            if (data.get(4).length()>0){
                GetSubjets(null, Integer.parseInt(data.get(4)));
            }


//            //ShoeAll();
//
//            boolean b=CheckData();
//            if (b){
//          //      suplist=SupListViewValues();
//            }else{
//
//            //    suplist= new ArrayList<>();
//            }


        }
        btndelete.setText(deleteTitle);
        idediciondoc.setText(title);
        btnguardardocenteedicion.setText(btnEditSave);
        btncancelardocenteedicion.setText(btnCancelReturn);
    }


    private void Config() {
        btnguardardocenteedicion.setOnClickListener(this);
        btncancelardocenteedicion.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        GetSubjets(null, 0);
        FillText();

    }
    private void GetValues (){

        if (etnuevodocentenombre.getText().toString().trim().length() > 0||etnuevodocenteapellido.getText().toString().trim().length() > 0||etnuevodocentealias.getText().toString().trim().length() > 0){
            String value=btnguardardocenteedicion.getText().toString();
            Teacher teacher= new Teacher();
            String nameLower=etnuevodocentenombre.getText().toString();
            String name=u.ToUpper(nameLower);
            String surnameLower=etnuevodocenteapellido.getText().toString();
            String surname=u.ToUpper(surnameLower);
            String nickLower=etnuevodocentealias.getText().toString();
            String nick=u.ToUpper(nickLower);
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

    public void onClick(View v){

        if (v==btnguardardocenteedicion){
            clic.start();
             if (etfill.getText().toString().length()>0&&!etfill.getText().toString().equals("Ninguna")){
             GetSubjets( etfill.getText().toString(), 0);}
             else {
                 tvidsubt.setText("0");
             }
             GetValues(); 
        }
        if (v==btncancelardocenteedicion){
            borrar.start();
            u.ClearTask(getApplicationContext());
        }
        if (v==btndelete){
            Alert(Integer.parseInt(data.get(0)));
            }
        }


    public void Alert(int id){

        AlertDialog.Builder dialog= new AlertDialog.Builder(AddTeacherActivity.this);
        dialog.setMessage("¿Eliminar?").setCancelable(true)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id= Integer.parseInt(data.get(1));

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

    private void FillText(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listSpin);
        AutoCompleteTextView textView = (AutoCompleteTextView)etfill;
        textView.setAdapter(adapter);
    }

    private void GetSubjets(String name, int idSub){
        sublist = new ArrayList<>();
        listSpin= new ArrayList<>();
        listSpin.add("Ninguna");
        Call<ArrayList<Subjet>> call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, null, 0);

        if (name!=null){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, name, 0);
        }else if(idSub!=0){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetById(idSub);
        }
        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

                if (response.code() == 200) {
                    sublist.addAll(response.body());

                    if (idSub!=0){
                        etfill.setText(sublist.get(0).getName());
                    }
                    if (name==null){

                        for (Subjet s: sublist){
                            listSpin.add(s.getName());}
                    }else  {
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

    public void Add(){

     Call<Void> call= ServiceBA.getInstance().createService(ISTeacher.class).Create(t);
        call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                        u.ClearTask(getApplicationContext());
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

