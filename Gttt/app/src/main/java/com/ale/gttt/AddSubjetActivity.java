package com.ale.gttt;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.AuxAdapter;
import com.ale.gttt.entities.AuxiliarSch;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubjetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstarttime, btnendtime, btncancelar, btnguardar, btndls, btnaddtime, btndeletesubjet;
    private EditText etst,etet, etnombremateria;
    private Spinner spindia;
    private int hour, minute;
    private TextView tvtitle;
    private AuxAdapter adapter;
    private ListView listdias;
    private CheckBox cbhc;
    private  ArrayList<String> data;
    private ArrayList<AuxiliarSch> values;
    MediaPlayer pop, borrar, clic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjet);
        Init();
        Verifications();
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
            title="Nueva Materia";
            btnEditSave="Guardar";
            btnCancelReturn="Cancelar";
            btndeletesubjet.setVisibility(View.INVISIBLE);

        }else{
            title="Editar/Eliminar Materia";
            btnEditSave="Guardar Cambios";
            btnCancelReturn="Volver";
            etnombremateria.setText(data.get(0));
            ArrayList<AuxiliarSch> a= TimeReader();
            Stack(a);

        }
        btndeletesubjet.setText(deleteTitle);
        tvtitle.setText(title);
        btnguardar.setText(btnEditSave);
        btncancelar.setText(btnCancelReturn);
    }

    private void Init() {
        values= new ArrayList<>();
        btndeletesubjet=findViewById(R.id.btndeletesubjet);
        btnstarttime=  findViewById(R.id.btnstarttime);
        btnaddtime=  findViewById(R.id.btnaddtime);
        btnendtime=  findViewById(R.id.btnendtime);
        etet=findViewById(R.id.etet);
        etst= findViewById(R.id.etst);
        btncancelar= findViewById(R.id.btncancelarmateria);
        btnguardar=  findViewById(R.id.btnas);
        btndls= findViewById(R.id.btndls);
        etst= findViewById(R.id.etst);
        etnombremateria=  findViewById(R.id.etnombremateria);
        spindia=  findViewById(R.id.spindia);
        listdias=  findViewById(R.id.listdias);
        cbhc=findViewById(R.id.cbhc);
        tvtitle=findViewById(R.id.tv_title_subjet);
        Intent dataintent= getIntent();
        data=dataintent.getStringArrayListExtra("arraydata");
        Config();
        GetAllSubjets();
    }

    public  void Config()
    {
        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        pop=MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);
        btnaddtime.setOnClickListener(this);
        btndls.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btnguardar.setOnClickListener(this);
        btndeletesubjet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnstarttime)
        {
            pop.start();
            final Calendar c= Calendar.getInstance();

            hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);
            TimePickerDialog dialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String st=hourOfDay+":"+minute;
                    etst.setText(st);
                }
            }, hour, minute, true);
            dialog.show();
        }
        if(v==btnendtime)
        {
            pop.start();
            final Calendar c= Calendar.getInstance();
            hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);
            TimePickerDialog dialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String et=hourOfDay+":"+minute;
                    etet.setText(et);
                }
            }, hour, minute, true);
            dialog.show();
        }
        if (v==btnguardar){
            clic.start();
            GetValues();
        }
        if (v==btncancelar){
            borrar.start();
            ClearTask();
        }
        if (v==btndls){
            pop.start();

                if(values!=null){
                    values.clear();
                    adapter.notifyDataSetChanged();
                }
        }
        if (v==btnaddtime){
            pop.start();
            GetTimes();
        }
        if (v==btndeletesubjet){
            Alert();
        }
    }



    private void GetValues (){

        if (etnombremateria.getText().toString().trim().length() > 0){
            String value=btncancelar.getText().toString();
            Subjet subjet= new Subjet();
            String nameLower=etnombremateria.getText().toString();
            String name=ToUpper(nameLower);
            String classes = new Gson().toJson(values);
            int id= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
            subjet.setIdUser(id);
            subjet.setName(name);
            if (values!=null){
                subjet.setClass_(classes);

            }else{
                subjet.setClass_("");
            }
            subjet.setActive(0);

            if (value.equals("Cancelar")){
                Save(subjet);
            }else if (value.equals("Volver")) {

                Edit(subjet);
            }
            }else{
            Toast.makeText(getApplicationContext(), "Faltan campos por completar", Toast.LENGTH_LONG).show();
            etnombremateria.requestFocus();
        }
      }

    private void Empty() {
        spindia.setSelection(0);
        etst.setText("");
        etet.setText("");

    }

     private void ClearTask(){
        Intent i= new Intent(getApplicationContext(), MenuTabActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private ArrayList<AuxiliarSch> TimeReader(){
        String stringh= data.get(2);
        Gson gson= new Gson();
        AuxiliarSch[] ax = gson.fromJson(stringh, AuxiliarSch[].class);
        ArrayList<AuxiliarSch>   auxArray= new ArrayList<>();

        Arrays.stream(ax).forEach( e -> { AuxiliarSch a= new AuxiliarSch();
          a.setDay(e.getDay());
          a.setStart(e.getStart());
          a.setEnd(e.getEnd());
          a.setType(e.getType());
          auxArray.add(a);
        });

        return auxArray;

    }
    private void Stack(ArrayList<AuxiliarSch> value){
        adapter=new AuxAdapter(getApplicationContext(), value);
        listdias.setAdapter(adapter);
    }
    private void GetTimes(){

        AuxiliarSch auxiliar = new AuxiliarSch();
        String day, start, end, title, classTime;
        day=spindia.getSelectedItem().toString();
        start=String.valueOf(etst.getText());
        end=String.valueOf(etet.getText());
        auxiliar.setDay(day);
        auxiliar.setStart(start);
        auxiliar.setEnd(end);

        if (!cbhc.isChecked()){
            title="Clase ";
        }else{
            title="Consulta ";
        }
        auxiliar.setType(title);



        values.add(auxiliar);

        if (etst.getText().toString().trim().length() > 0&& etet.getText().toString().trim().length() > 0) {

            Stack(values);
        } else {
            values=null;
            //   Stack(values);
        }
        Empty();
    }
    private void Save(Subjet s){

        Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).Create(s);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_LONG).show();
                    Empty();
                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al guardar: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void Edit(Subjet subjet) {
//        Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).Update(subjet);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.code()==200){
//                    Toast.makeText(getApplicationContext(), "Cambios realizados!", Toast.LENGTH_LONG).show();
//                    Empty();
//                }else if(response.code()==404){
//                    Toast.makeText(getApplicationContext(), "Recurso no encontrado", Toast.LENGTH_LONG).show();
//                }else if(response.code()==500){
//                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Error al editar: "+t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

    }
    private void GetAllSubjets(){
        int id= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        Call<ArrayList<Subjet>> call= ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, null, 0);
        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al cargar: "+t.getMessage(), Toast.LENGTH_LONG ).show();

            }
        });

    }
    private String ToUpper(String string){
        String str = string;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());

        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        return str;
    }
    public void Alert(){


                AlertDialog.Builder dialog= new AlertDialog.Builder(AddSubjetActivity.this);
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

}
