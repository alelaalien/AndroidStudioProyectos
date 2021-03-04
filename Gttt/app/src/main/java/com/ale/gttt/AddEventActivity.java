package com.ale.gttt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISType;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.Checks;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.TypeOf;
import com.ale.gttt.io.ServiceBA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity  implements View.OnClickListener  {
private Button   btndelete, btncancelarnuevoevento, btnaceptarnuevoevento, btnselecttype;
MediaPlayer pop, borrar, clic;
private String a, datatype;
private TextView btnaddnew;

private  ArrayList<String> data;
private  ArrayList<String> backvalues;
private int hour, minute, day, month, year, idU;
private String[] listSpin;
private RadioGroup rgeventonuevo;
private RadioButton rbaltanuevoevento, rbmedianuevoevento, rbbajanuevoevento;
private EditText etnotes, etfecha, ethora, ettitle;
private Spinner spinnuevoevento, spinnew;
private int[] rts;
private ArrayList<Subjet> sublist;
private ToggleButton tbe;
private TextView tvevent, tvidsub, tvidtype;
private  int y;
private Utilities u;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Init();
    }

    private  void  Init(){
        btnaceptarnuevoevento= findViewById(R.id.btnguardarnuevoevento);
        btncancelarnuevoevento= findViewById(R.id.btncancelarnuevoevento);
        btndelete=findViewById(R.id.btneventdelete);
        tvidsub=findViewById(R.id.tvidsube);
        btnselecttype=findViewById(R.id.btnselecttype);
        ettitle=findViewById(R.id.ettitle);
        rts=new int[1];
        rgeventonuevo=findViewById(R.id.rgeventonuevo);
        rbaltanuevoevento=findViewById(R.id.rbaltanuevoevento);
        rbbajanuevoevento=findViewById(R.id.rbbajanuevoevento);
        etnotes=findViewById(R.id.etnotes);
        etnotes.setSelectAllOnFocus(true);
        etfecha=findViewById(R.id.etfecha);
        a= SharedPreferenceManager.getInstance(getApplicationContext()).GetToken();
        ethora=findViewById(R.id.ethora);
        tvevent=findViewById(R.id.tvtitleevent);
        btnaddnew=findViewById(R.id.btnaddnew);
        rbmedianuevoevento=findViewById(R.id.rbmedianuevoevento);
        tvidtype=findViewById(R.id.tvidtype);
        spinnuevoevento=findViewById(R.id.spinnuevoevento);
        u= new Utilities();
        idU= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        Intent dataintent= getIntent();
        data=dataintent.getStringArrayListExtra("arrayE");
        datatype=dataintent.getStringExtra("types");
      //  backvalues=dataintent.getStringArrayListExtra("backval");
       if (backvalues!=null){
            for(String s:backvalues){
                System.out.println(s);
            }
        }

        if (datatype!=null){
            btnaddnew.setText(datatype);
        }
        Config();
        Verifications();
        GetSubjets(null, 0);
    }

    private void Config() {
        pop = MediaPlayer.create(this, R.raw.pop);
        borrar = MediaPlayer.create(this, R.raw.borrar);
        clic = MediaPlayer.create(this, R.raw.clic);
        btnaceptarnuevoevento.setOnClickListener(this);
        btncancelarnuevoevento.setOnClickListener(this);
        etfecha.setOnClickListener(this);
        ethora.setOnClickListener(this);
        btnselecttype.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        if(tvevent.getText().toString().equals("Nuevo Evento")){
            Enabled(true);
        }else{
            Enabled(false);
        }
    }

    private void Verifications() {

     if (data!=null){
         SetTexts(1 ); }
     else{ SetTexts(0 );
     }
    }

    private void SetTexts(int i ) {

        String title, btnEditSave, btnCancelReturn;
        String deleteTitle="Eliminar";
        String ide, idsub,typeof, date,priority, notes, hour, dateString, titlee;

        if (i==0){
            
            title="Nuevo Evento";
            btnEditSave="Guardar";
            btnCancelReturn="Cancelar";
            btndelete.setVisibility(View.INVISIBLE);
        if (backvalues!=null){
            BackValues(backvalues);
        }

        }else{
            title="Editar Evento";
            btnEditSave="Editar";
            btnCancelReturn="Volver";

 if (backvalues==null){
     ide=data.get(0);
     idsub=data.get(1);//spinner
     Enabled(false);
     date=data.get(2);
     priority=data.get(6);
     notes=data.get(5);
     titlee=data.get(4);
     int val=Integer.parseInt(data.get(3));
     int p=Integer.parseInt(priority);
     hour=date.substring(date.indexOf("T")+1, date.length());
     dateString=date.substring(0, date.indexOf("T"));

     RadioCheck(p);

     etnotes.setText(notes);
     ethora.setText(hour);
     etfecha.setText(dateString);
     ettitle.setText(titlee);

 }else {
     BackValues(backvalues);
 }

        }
        btndelete.setText(deleteTitle);
        tvevent.setText(title);
        btnaceptarnuevoevento.setText(btnEditSave);
        btncancelarnuevoevento.setText(btnCancelReturn);
    }

    private void RadioCheck(int p) {

        if (p==0){
            rbaltanuevoevento.setChecked(true);
        }else if (p==1){
            rbmedianuevoevento.setChecked(true);
        }else if (p==2){
            rbbajanuevoevento.setChecked(true);
        }
    }


    private void BackValues(ArrayList<String> s) {


        int p=Integer.parseInt(s.get(6));
        ettitle.setText(s.get(4));
        etfecha.setText(s.get(2));
        ethora.setText(s.get(7));
        etnotes.setText(s.get(5));

        RadioCheck(p);
    }


    public void onClick(View v){

        final Calendar c= Calendar.getInstance();
     //   c.add(Calendar.DATE, -1);
        if (v==btndelete){
            Alert();
        }
        if(v==ethora){
            pop.start();
            hour=c.get(Calendar.HOUR_OF_DAY);
            minute=c.get(Calendar.MINUTE);
            TimePickerDialog dialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String h, m;
                    if (hourOfDay<10){
                         h="0"+hourOfDay;
                    }else{
                        h=String.valueOf(hourOfDay);
                    }
                    if (minute<10){
                        m="0"+minute;
                    }else{
                        m=String.valueOf(minute);
                    }
                    String st=h+":"+m;
                    ethora.setText(st);
                }
            }, hour, minute, true);

            dialog.show();
        }
        if (v==btnaddnew){

        }
        if (v==etfecha){
            pop.start();
            day=c.get(Calendar.DAY_OF_MONTH);
            month=c.get(Calendar.MONTH);
            year=c.get(Calendar.YEAR);
            DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    int m2= month+1;
                    String date= year+"-"+m2+"-"+dayOfMonth;
                    Log.d("diia", day+"");
                    etfecha.setText(date);
                }
            }, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            dialog.show();
        }
        String action, acept, edit;
        action=btnaceptarnuevoevento.getText().toString();
        acept="Aceptar";
        edit="Editar";

        if (v==btnaceptarnuevoevento){
            clic.start();
            if(action.equals("Editar")){
                Enabled(true);
                btnaceptarnuevoevento.setText(acept);
            }else if(action.equals("Guardar")||action.equals("Aceptar")){
                Enabled(false);
                GetValues();
                btnaceptarnuevoevento.setText(edit);
            }

        }
        if (v==btncancelarnuevoevento){
            borrar.start();
            u.ClearTask(getApplicationContext());
        }
        if(v==btnselecttype){

            SelectingType();


        }


    }

    private void SelectingType() {

        ArrayList<String> values=new ArrayList<>();
        String title, date, time, notes, prioritys, mat;
        if (ettitle.getText().toString().length()>0){
            title=ettitle.getText().toString();
        }else{
            title=null;
        }
        if (etfecha.getText().toString().length()>0){
            date=etfecha.getText().toString();
        }else{
            date=null;
        }
        if (ethora.getText().toString().length()>0){
            time=ethora.getText().toString();
        }else{
            time=null;
        }
        if (etnotes.getText().toString().length()>0){
            notes=etnotes.getText().toString();
        }else{
            notes=null;
        }
        int p= GetPriority();
        prioritys=String.valueOf(p);
        mat=spinnuevoevento.getSelectedItem().toString();
        if (data!=null){
            values.add(String.valueOf(data.get(0)));
            values.add(String.valueOf(data.get(1)));
            values.add(date);//2
            values.add("");//3
            values.add(title);//5
            values.add(notes);//6
            values.add(prioritys);//4
            values.add(time);//7
        }else{
            values.add("");
            values.add("");
            values.add(date);//2
            values.add("");//3
            values.add(title);//5
            values.add(notes);//6
            values.add(prioritys);//4
            values.add(time);//7
        }



        Enabled(false);

        Intent i= new Intent(getApplicationContext(), Checks.class);
        i.putStringArrayListExtra("dataaddevent", values);
        startActivity(i);
    }

    public void onBackPressed( ){
        u.ClearTask(getApplicationContext());
    }

    private void GetValues(){


        String value=btncancelarnuevoevento.getText().toString();
        String notes, title, date, type, subjet, sid, day, time, typeOfs;
        int  idSubjet, active, priority, typeOf;
        typeOfs=btnaddnew.getText().toString();
        GetType(typeOfs);


        if (ettitle.getText().toString().length()>0&&etfecha.getText().toString().length()>0){
            Event e=new Event();
            notes=etnotes.getText().toString();
            title=ettitle.getText().toString();

            priority= GetPriority();
            subjet=spinnuevoevento.getSelectedItem().toString();
            if (!subjet.equals("Seleccionar")){
                GetSubjets(subjet, 0);
            }else{
                Toast.makeText(getApplicationContext(), "Seleccionar materia para continuar", Toast.LENGTH_LONG).show();
                return;
            }
            sid=tvidsub.getText().toString();
            System.out.println("ids "+sid);

               idSubjet=Integer.parseInt(sid);

            active=0;
            day=etfecha.getText().toString();
            time=ethora.getText().toString();

            date=day+"T"+time+":00";
System.out.println();
String b=tvidtype.getText().toString();
            int r=rts[0];
            e.setTypeId(r);
            e.setTitle(title);
            e.setNotes(notes);
            e.setActive(active);
            e.setPriority(priority);
            e.setIdUser(idU);
            e.setDate(date);
            e.setIdSubjet(idSubjet);


            if (value.equals("Cancelar")){
                try {



               Add(e);
                }catch (Exception ex){
                    ex.getMessage();
                }

            }else if (value.equals("Volver")) {
              e.setId(Integer.parseInt(data.get(0)));
               Edit(e);
            }
        }else{
        Toast.makeText(getApplicationContext(), "Faltan campos por completar", Toast.LENGTH_LONG).show();
    }
 }

    private int GetPriority() {
        int priority;
        int radio=rgeventonuevo.getCheckedRadioButtonId();
        if (radio==rbaltanuevoevento.getId()){
            priority=0;
        }else if (radio==rbmedianuevoevento.getId()){
            priority=1;
        }else{
            priority=2;
        }
        return priority;
    }

    private void Enabled(boolean b){
        rgeventonuevo.setEnabled(b);
        spinnuevoevento.setEnabled(b);
        etnotes.setEnabled(b);
        ettitle.setEnabled(b);
        etfecha.setEnabled(b);
        ethora.setEnabled(b);
    }

    private Date Date(String s){

        Date date=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
              date = dateFormat. parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void Alert(){

        AlertDialog.Builder dialog= new AlertDialog.Builder(AddEventActivity.this);
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

    public void GetType(String type){

        Call<ArrayList<TypeOf>> call=ServiceBA.getInstance().createService(ISType.class).GetAllFilters(idU, type);
        call.enqueue(new Callback<ArrayList<TypeOf>>() {
            @Override
            public void onResponse(Call<ArrayList<TypeOf>> call, Response<ArrayList<TypeOf>> response) {
                if (response.code()==200){
                    ArrayList<TypeOf>al=new ArrayList<>();
                    al.addAll(response.body());
                    int m=al.get(0).getId();
                    y=m;
                    rts[0]=m;
                    String h=String.valueOf(m);
                    tvidtype.setText(h);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<TypeOf>> call, Throwable t) {

            }
        });
    }

    public void Add(Event e){
            Call<Void> call= ServiceBA.getInstance().createService(ISEvent.class).Create(e);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int code= response.code();
                    if (code==200){
                        Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_SHORT).show();
                            u.ClearTask(getApplicationContext());
                    }else if (code==404){
                        Toast.makeText(getApplicationContext(), "Recursos no disponibles", Toast.LENGTH_SHORT).show();

                    }else if (code==500){
                        Toast.makeText(getApplicationContext(), "Error del servidor: 500", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sin conexión"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void Edit(Event e){

            Call<Event> call= ServiceBA.getInstance().createService(ISEvent.class).Update(e.getId(), e);
            call.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    int code= response.code();
                    if (code==200){
                        Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_SHORT).show();
                        u.ClearTask(getApplicationContext());

                    }else if (code==404){
                        Toast.makeText(getApplicationContext(), "Recursos no disponibles", Toast.LENGTH_SHORT).show();

                    }else if (code==500){
                        Toast.makeText(getApplicationContext(), "Error del servidor: 500", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void Delete(int id){
            Call<Void> call= ServiceBA.getInstance().createService(ISEvent.class).Delete(id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    int code= response.code();
                    if (code==200){
                        Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_SHORT).show();


                    }else if (code==404){
                        Toast.makeText(getApplicationContext(), "Recursos no disponibles", Toast.LENGTH_SHORT).show();

                    }else if (code==500){
                        Toast.makeText(getApplicationContext(), "Error del servidor: 500", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void GetSubjets(String name, int idSub){

        sublist=new ArrayList<>();
        Call<ArrayList<Subjet>> call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idU, null, 0);

        if (name!=null){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idU, name, 0);
        }else if(idSub!=0){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetById(a, idSub);
        }else{
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idU, null, 0);
        }
        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

                if (response.code() == 200) {

                    sublist.addAll(response.body());
                    listSpin=new String[sublist.size()];
                    if (idSub!=0){//buscando el nombre de la materia
                       // etfill.setText(sublist.get(0).getName());
                    }
                    if (name==null){//cargar todas

                        for (int i=0; i<sublist.size() ; i++){

                            listSpin[i]=sublist.get(i).getName();
                        }
                        SpinnerList(listSpin);

                    }else  {
                        int l=response.body().size();
                        if (l==0){
                            tvidsub.setText("0");
                        }else{
                            tvidsub.setText(String.valueOf(sublist.get(0).getId()));
                        }
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();

                } else if (response.code() == 500) {

                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Error 500", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {
            }
        });
    }

    private void SpinnerList(String[] listSpin) {

        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this, R.layout.spinner_item, listSpin);

        spinnuevoevento.setAdapter(adapter);
        spinnuevoevento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetSubjets(spinnuevoevento.getSelectedItem().toString(), 0);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
