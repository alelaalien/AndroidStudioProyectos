package com.ale.gttt;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISUser;
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

    private Button btnstarttime, btnendtime, btncancelar, btnguardar,  btnaddtime, btndeletesubjet;
    private EditText etst,etet, etnombremateria;
    private Spinner spindia;
    private int hour, minute;
    private TextView tvtitle;
    private AuxAdapter adapter;
    private ListView listdias;
    private CheckBox cbhc;
    private  ArrayList<String> data;
    private ArrayList<AuxiliarSch> editValues;
    private ArrayList<AuxiliarSch> suplist = new ArrayList<>();
   private ArrayList<AuxiliarSch> a = new ArrayList<>();
    private String data2;
    private ArrayList<AuxiliarSch> values= new ArrayList<>();
    MediaPlayer pop, borrar, clic;
    private int idU;


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
            title="Editar/Eliminar Materia";
            btnEditSave="Guardar Cambios";
            btnCancelReturn="Volver";
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

    private void Init() {

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
        spindia=  findViewById(R.id.spindia);
        listdias=  findViewById(R.id.listdias);
        cbhc=findViewById(R.id.cbhc);
        tvtitle=findViewById(R.id.tv_title_subjet);
        idU=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        Intent dataintent= getIntent();
        data=dataintent.getStringArrayListExtra("arraydata");

        Config();
    }

    public  void Config()
    {
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
                        Log.d("va", a.size()+"");     Log.d("va",  " "+position);
                        Stack(suplist);
                    }
                }
            }
        });



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

        }else{////////////////////////////////////////////////////edit////////////////////////


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
        Call<Subjet> call= ServiceBA.getInstance().createService(ISSubjet.class).Update(subjet.getId(), subjet);
        call.enqueue(new Callback<Subjet>() {
            @Override
            public void onResponse(Call<Subjet> call, Response<Subjet> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), "Cambios realizados!", Toast.LENGTH_LONG).show();
                    Empty();
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

        public void Delete(int id) {
            Call<Void> call= ServiceBA.getInstance().createService(ISSubjet.class).Delete(id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

}
