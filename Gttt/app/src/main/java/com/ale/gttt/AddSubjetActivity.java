package com.ale.gttt;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
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

import com.ale.gttt.Interfaces.ISDictates;
import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.AuxAdapter;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.AuxiliarSch;
import com.ale.gttt.entities.Dictates;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.entities.User;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubjetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstarttime, btnendtime, btncancelar, btnguardar,  btnaddtime, btndeletesubjet;
    private EditText etst,etet, etnombremateria;
    private String t;
    private Spinner spindia;
    private int hour, minute;
    private TextView tvtitle;
    private AuxAdapter adapter;
    private ListView listdias;
    private CheckBox cbhc;
    private User user;
    private  ArrayList<String> data;
    private ArrayList<Dictates> dic;
    private ArrayList<AuxiliarSch> suplist = new ArrayList<>();
    private String data2, a;
    private ArrayList<AuxiliarSch> values= new ArrayList<>();
    MediaPlayer pop, borrar, clic;
    private int idU;
    private Utilities u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjet);
        Init();
        Verifications();
    }

    private void Init() {
        u=new Utilities();
        btndeletesubjet=findViewById(R.id.btndeletesubjet);
        btnstarttime=  findViewById(R.id.btnstarttime);
        btnaddtime=  findViewById(R.id.btnaddtime);
        btnendtime=  findViewById(R.id.btnendtime);
        etet=findViewById(R.id.etet);
        etst= findViewById(R.id.etst);
        btncancelar= findViewById(R.id.btncancelarmateria);
        btnguardar=  findViewById(R.id.btnas);
        etst= findViewById(R.id.etst);
        etnombremateria=  findViewById(R.id.etnombremateria);
        etnombremateria.requestFocus();
        spindia=  findViewById(R.id.spindia);
        listdias=  findViewById(R.id.listdias);
        cbhc=findViewById(R.id.cbhc);
        user=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser();
         a= SharedPreferenceManager.getInstance(getApplicationContext()).GetToken();
        dic=new ArrayList<>();
        tvtitle=findViewById(R.id.tv_title_subjet);
        idU=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        t=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getToken();
        Intent dataintent= getIntent();

        data=dataintent.getStringArrayListExtra("arraydata");
        Config();
    }

    public  void Config(){

        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        pop=MediaPlayer.create(this, R.raw.pop);
        borrar=MediaPlayer.create(this, R.raw.borrar);
        clic=MediaPlayer.create(this, R.raw.clic);
        btnaddtime.setOnClickListener(this);
        btncancelar.setOnClickListener(this);
        btnguardar.setOnClickListener(this);
        btndeletesubjet.setOnClickListener(this);
        listdias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (btncancelar.getText().toString().equals("Cancelar")){
                    values.remove(position);
                    adapter.notifyDataSetChanged();
                }else {
                    boolean val= CheckData();
                    if (val){
                        suplist.remove(position);
                        adapter.notifyDataSetChanged();

                        Stack(suplist);
                    }
                }
            }
        });
    }

    private void Verifications() {

        if (data!=null){
            SetTexts(1);
            CheckData();

        }else{
            SetTexts(0);
        }
    }

    private boolean CheckData() {
        boolean value=false;
        if (data.get(2).length()>0){
            data2=data.get(2);
            value=true;
        }
        return value;
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
            title="Editar Materia";
            btnEditSave="Editar";
            btnCancelReturn="Volver";
            Enabled(false);
            etnombremateria.setText(data.get(0));
            Extra();
            boolean b=CheckData();
            if (b){
                 suplist=SupListViewValues();
            }else{

                suplist= new ArrayList<>();
            }


        }
        btndeletesubjet.setText(deleteTitle);
        tvtitle.setText(title);
        btnguardar.setText(btnEditSave);
        btncancelar.setText(btnCancelReturn);
    }

    private ArrayList<AuxiliarSch> Extra() {
        ArrayList<AuxiliarSch> a;
        String extra= data.get(2);
        if (extra.length()!=0){
            a= TimeReader(); //2
            Stack(a);
        }else{
            a= new ArrayList<>();
        }
        return a;

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
            if (btnguardar.getText().toString().equals("Volver")){
                String action, acept, edit;
                action=btnguardar.getText().toString();
                acept="Aceptar";
                edit="Editar";



                if(action.equals("Editar")){
                    Enabled(true);
                    btnguardar.setText(acept);
                }else if(action.equals("Guardar")||action.equals("Aceptar")){
                    Enabled(false);
                    GetValues();
                    btnguardar.setText(edit);
                }
            }else {
                GetValues();
            }






        }
        if (v==btncancelar){
            borrar.start();
            u.ClearTask(getApplicationContext());
        }

        if (v==btnaddtime){
            pop.start();
     if (btncancelar.getText().toString().equals("Volver")){
                GetTimes(1);
            }else{
                GetTimes(0);
            }
        CloseInput();

        }
        if (v==btndeletesubjet){
            Alert();
        }
    }

    private void Enabled(boolean b) {
        etnombremateria.setEnabled(b);
        etet.setEnabled(b);
        etst.setEnabled(b);
    }

    private void CloseInput() {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(etet.getWindowToken(), 0);

    }

    private ArrayList<AuxiliarSch> SupListViewValues(){
      Adapter a=  listdias.getAdapter();
     int  i=a.getCount();
     ArrayList<Object> ar= new ArrayList<>();
     suplist= new ArrayList<>();
     for(int d=0; d<i;d++){
         ar.add(a.getItem(d));
     }
        for(int f=0; f<ar.size();f++){
           AuxiliarSch au=(AuxiliarSch) ar.get(f);
           suplist.add(au);
        }
return suplist;
    }

    private void GetValues (){

        if (etnombremateria.getText().toString().trim().length() > 0){
            String value=btncancelar.getText().toString();
            Subjet subjet= new Subjet();
            String nameLower=etnombremateria.getText().toString();
            String name=ToUpper(nameLower);
            int id= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
            subjet.setIdUser(id);
            subjet.setName(name);
            subjet.setActive(0);

            if (value.equals("Cancelar")){
                String classes = new Gson().toJson(values);
                if (values!=null){
                    subjet.setClass_(classes);

                }else{
                    subjet.setClass_("");
                }
                Save(subjet);
            }else if (value.equals("Volver")) {


                String classes = new Gson().toJson(suplist);
                if (suplist!=null){
                    subjet.setClass_(classes);

                }else{
                    subjet.setClass_("");
                }
                    int idSubjet=Integer.parseInt(data.get(1));
                    subjet.setId(idSubjet);
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

    private AuxAdapter Stack(ArrayList<AuxiliarSch> value){
        adapter=new AuxAdapter(getApplicationContext(), value);
        listdias.setAdapter(adapter);
         return adapter;
    }

    private void GetTimes(int val){
        AuxiliarSch auxiliar = GetAuxiliar();
        if (val==0){

            if (etst.getText().toString().trim().length() > 0&& etet.getText().toString().trim().length() > 0) {
                values.add(auxiliar);
                } Stack(values);

        }else{


            if (etst.getText().toString().trim().length() > 0&& etet.getText().toString().trim().length() > 0) {
                suplist.add(auxiliar);

            }
            Stack(suplist);
        }

      Empty();
    }

    private AuxiliarSch GetAuxiliar() {
        AuxiliarSch auxiliar = new AuxiliarSch();
        String day, start, end, title;
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
        return auxiliar;
    }

    private void Save(Subjet s){
        Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).Create(a, s);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_LONG).show();
                    Empty();
                    u.ClearTask(getApplicationContext());

                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                }else if(response.code()==204){
                    Toast.makeText(getApplicationContext(), "204", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al guardar: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void Edit(Subjet subjet) {
        Call<Subjet> call= ServiceBA.getInstance().createService(ISSubjet.class).Update(a, subjet.getId(), subjet);
        call.enqueue(new Callback<Subjet>() {
            @Override
            public void onResponse(Call<Subjet> call, Response<Subjet> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Cambios realizados!", Toast.LENGTH_LONG).show();
                    Empty();
                    u.ClearTask(getApplicationContext());
                }else if(response.code()==404){
                    Toast.makeText(getApplicationContext(), "Recurso no encontrado", Toast.LENGTH_LONG).show();
                }else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Subjet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al editar: "+t.getMessage(), Toast.LENGTH_LONG).show();
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
                                    DeleteEvents(id);
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
        title.setMessage("Los eventos relacionados se perderán");
                title.show();
    }

    public void Delete(int id) {

            Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).Delete(a, id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    u.ClearTask(getApplicationContext());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

    public void onBackPressed( ){
        u.ClearTask(getApplicationContext());
    }

    private void DeleteEvents(int id){
        Call<Void> call= ServiceBA.getInstance().createService(ISEvent.class).DeleteBySubjet(idU, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                     DeleteRelations();
                }else if (response.code()==404){
                    Toast.makeText(getApplicationContext(), "Recurso no encontrado", Toast.LENGTH_LONG).show();
                }else if (response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error del servidor. Reintenar", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void DeleteRelations() {

        int idSubjet=Integer.parseInt(data.get(1));


           Call<Void>call=ServiceBA.getInstance().createService(ISDictates.class).DeleteBySubjet(idSubjet);
           call.enqueue(new Callback<Void>() {
               @Override
               public void onResponse(Call<Void> call, Response<Void> response) {
                   if (response.code()==200){

                       Delete(idSubjet);

                   }else if (response.code()==404){
                       Toast.makeText(getApplicationContext(), "Recurso no encontrado", Toast.LENGTH_LONG).show();
                   }else if (response.code()==500){
                       Toast.makeText(getApplicationContext(), "Error del servidor. Reintenar", Toast.LENGTH_LONG).show();

                   }
               }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {
                   Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();

               }
           });


    }

    private void GetDictates(){
        int idSubjet=Integer.parseInt(data.get(1));
        Call<ArrayList<Dictates>> call=ServiceBA.getInstance().createService(ISDictates.class).GetAllBySubjet(idSubjet);
        call.enqueue(new Callback<ArrayList<Dictates>>() {
            @Override
            public void onResponse(Call<ArrayList<Dictates>> call, Response<ArrayList<Dictates>> response) {
                if (response.code()==200){
                    if (response.body().size()>0){
                        dic.addAll(response.body());
                    }

                }else if (response.code()==404){
                    Toast.makeText(getApplicationContext(), "Recurso no encontrado", Toast.LENGTH_LONG).show();
                }else if (response.code()==500){
                    Toast.makeText(getApplicationContext(), "Error del servidor. Reintenar", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Dictates>> call, Throwable t) {
             Toast.makeText(getApplicationContext(), "Error "+t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}
