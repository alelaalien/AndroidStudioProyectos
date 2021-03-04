package com.ale.gttt;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ale.gttt.Interfaces.ISDictates;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.CheckboxsActivity;
import com.ale.gttt.Tools.DictatesAdapter;
import com.ale.gttt.Tools.StringAdpater;
import com.ale.gttt.Tools.SubjetsAdapter;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.AuxiliarSch;
import com.ale.gttt.entities.Dictates;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnguardardocenteedicion, btncancelardocenteedicion, btndelete, btnselectsubjet, btnaddspin;
    private EditText etnuevodocentenombre, etnuevodocentealias, etnuevodocentetelefono, etnuevodocenteemail, etnuevodocenteapellido;
    MediaPlayer pop, borrar, clic;
    private TextView idediciondoc,   tvidsubt;
    private int idUser;
    private String a;
    private ListView lv;
    private ArrayList<Dictates> dic;
    private ArrayList<String> data;
    private  String[]  tryintent;
    private StringAdpater ad;
    private ListView lvg;
    private ArrayList<Subjet> sublist;
    private ArrayList<Subjet> sublistnames;
    private ArrayList<String> sublist2;
private Spinner spinselects;
    private SubjetsAdapter sn;
    private Utilities u;
    private ArrayList<Integer> tomove= new ArrayList<>();
    private  ArrayList<Integer> tosave= new ArrayList<>();

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
            sublistnames=new ArrayList<>();
            spinselects=findViewById(R.id.spinselects);
            btnaddspin=findViewById(R.id.btnaddspin);
            lvg=findViewById(R.id.lvsubnames);
            idUser=SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
            idediciondoc=findViewById(R.id.idediciondoc);
            btncancelardocenteedicion= findViewById(R.id.btncancelardocenteedicion);
            btnguardardocenteedicion= findViewById(R.id.btnguardardocenteedicion);
            etnuevodocentenombre= findViewById(R.id.etnuevodocentenombre);
            etnuevodocentealias= findViewById(R.id.etnuevodocentealias);
            etnuevodocentetelefono= findViewById(R.id.etnuevodocentetelefono);
            etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
            sublist=new ArrayList<>();
            etnuevodocenteemail = findViewById(R.id.etnuevodocenteemail);
            etnuevodocenteapellido= findViewById(R.id.etnuevodocenteapellido);
            dic=new ArrayList<>();
            sublist2=new ArrayList<>();
            lv=findViewById(R.id.lvtich);
            pop= MediaPlayer.create(this, R.raw.pop);
            borrar=MediaPlayer.create(this, R.raw.borrar);
            clic=MediaPlayer.create(this, R.raw.clic);
            Intent dataintent= getIntent();
            a= SharedPreferenceManager.getInstance(getApplicationContext()).GetToken();
            data=dataintent.getStringArrayListExtra("datat");
            tryintent=dataintent.getStringArrayExtra("try");



            Config();
    }

    private void Verifications() {

        if (data!=null){
            SetTexts(1);
            GetDictates();
            getTry();
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

        }
        btndelete.setText(deleteTitle);
        idediciondoc.setText(title);
        btnguardardocenteedicion.setText(btnEditSave);
        btncancelardocenteedicion.setText(btnCancelReturn);
    }

    private void Config() {

        btnguardardocenteedicion.setOnClickListener(this);
        btncancelardocenteedicion.setOnClickListener(this);
        btnaddspin.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        GetSubjet();

        ad=new StringAdpater(getApplicationContext(),sublist2 );
        lv.setAdapter(ad);
        sn=new SubjetsAdapter(getApplicationContext(),sublistnames, 1 );
        lvg.setAdapter(sn);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name=sublist2.remove(position);
                        GetSubjetsId(name, 1, 1);
                        ad.notifyDataSetChanged();

                }
        });
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
            //SaveDic(1,2);
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

    private void getTry(){
            int i=tryintent.length;
            if (i!=0){
                for (String s:tryintent){
                    sublist2.add(s);

                }

            }
    }

    private void Edit(Teacher teacher) {
if (sublist2.size()>0){
    for(String s: sublist2){
        GetSubjetsId(s, 0, 1);
    }

}  IteratorD();
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

        if (v==btnaddspin){

            if (!spinselects.getSelectedItem().toString().equals("Seleccionar")&&spinselects.getSelectedItem()!=null) {
                String name=spinselects.getSelectedItem().toString();
                if (!sublist2.contains(name)){
                    sublist2.add(name);
                }

                ad.notifyDataSetChanged();
            }


        }

        if (v==btnguardardocenteedicion){

            ArrayList <Subjet>s=GetAdapter();
            System.out.println(s.size()+"  traido de adapter");
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
            IteratorD();
             Alert();
            }
        if(v==btnselectsubjet){
            Intent i=new Intent(getApplicationContext(), CheckboxsActivity.class);
            startActivity(i);
        }

        }

    private void Enabled(boolean b) {
        etnuevodocentealias.setEnabled(b);
        etnuevodocenteapellido.setEnabled(b);
        etnuevodocentetelefono.setEnabled(b);
        etnuevodocenteemail.setEnabled(b);
        etnuevodocentenombre.setEnabled(b);

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

    private void GetSubjet(){


        Call<ArrayList<Subjet>> call  = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idUser, null, 0);

        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

                if (response.code() == 200) {
                    sublist.addAll(response.body());
                    Spinn(sublist);


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

    private void Spinn(ArrayList<Subjet> sublist) {
        ArrayList<String> array= new ArrayList<>();
        array.add("Seleccionar");
        for (int i=0; i<sublist.size(); i++){
            int n=i+1;
            array.add(sublist.get(i).getName());
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_subjet_item, array);
        spinselects.setAdapter(adapter);


    }

    private void Delete(int id) {
        DeleteRelations();

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

     Call<Teacher> call= ServiceBA.getInstance().createService(ISTeacher.class).Create(t);
        call.enqueue(new Callback<Teacher>() {
                @Override
                public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                    if(response.code()==200){
                        Teacher t=response.body();
                        int id=t.getId();


                   if (sublist2.size()>0){
                       for(String s: sublist2){
                           GetSubjetsId(s, id, 0);
                       }

                   }

                }



                    Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                        u.ClearTask(getApplicationContext());
                }

                @Override
                public void onFailure(Call<Teacher> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void SaveDic(Dictates d){

      Call<Void> call=ServiceBA.getInstance().createService(ISDictates.class).Create(d);
      call.enqueue(new Callback<Void>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {
              if (response.code()==200){
                  Log.d("hecho", "");
              }
              if (response.code()==404){
                  Log.d("no encontrado", "");
              }
              if (response.code()==500){
                  Log.d("error 500", "");
              }
          }

          @Override
          public void onFailure(Call<Void> call, Throwable t) {

                  Log.d("fail", ""+t.getMessage());

          }
      });


    }

    private ArrayList<Subjet> GetAdapter(){
         ArrayList<Subjet>suplist= new ArrayList<>();
            Adapter a=  lvg.getAdapter();
            int  i=a.getCount();
            ArrayList<Object> ar= new ArrayList<>();

            for(int d=0; d<i;d++){
                ar.add(a.getItem(d));
            }
            for(int f=0; f<ar.size();f++){
                Subjet au=(Subjet) ar.get(f);
                suplist.add(au);
            }
            return suplist;

    }

    private void DeleteRelations( ) {

        int idt=Integer.parseInt(data.get(0));


            Call<Void>call=ServiceBA.getInstance().createService(ISDictates.class).DeleteByTeacher(idt);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code()==200){

                        Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG).show();


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
        int idT=Integer.parseInt(data.get(0));
        Call<ArrayList<Dictates>> call=ServiceBA.getInstance().createService(ISDictates.class).GetAllByTeacher(idT);
        call.enqueue(new Callback<ArrayList<Dictates>>() {
            @Override
            public void onResponse(Call<ArrayList<Dictates>> call, Response<ArrayList<Dictates>> response) {
                if (response.code()==200){
                    if (response.body().size()>0){
                        dic.addAll(response.body());

                        ad.notifyDataSetChanged();
                         //   GetSubjets(dic);


                    }else {
                        GetSubjet();
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

    private void GetSubjetsId(String name, int val, int t) {//guardar para editar

        if (val==0){//editar t 1
            Call<ArrayList<Subjet>>call= ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idUser, name, 0);
            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code()==200){
                        sublistnames.addAll(response.body());
                        if (response.body()!=null){
                   Dictates d= new Dictates();
                   d.setSubjetId(response.body().get(0).getId());
                   d.setTeacherId(Integer.parseInt(data.get(0)));
                   SaveDic(d);
                        }
                        sn.notifyDataSetChanged();


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {

                }
            });
        }else if(t==0) {//guardar
            Call<ArrayList<Subjet>>call= ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idUser, name, 0);
            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code()==200){
                        sublistnames.addAll(response.body());
                        if (response.body()!=null){
                            Dictates d= new Dictates();
                            d.setSubjetId(response.body().get(0).getId());
                            d.setTeacherId(val);
                            SaveDic(d);
                        }
                        sn.notifyDataSetChanged();


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {

                }
            });

        }else{
            Call<ArrayList<Subjet>>call= ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, idUser, name, 0);
            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code()==200){
                        sublistnames.addAll(response.body());

                        sn.notifyDataSetChanged();


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {

                }
            });
            }
        }



    private void IteratorD(){
      ArrayList<Subjet> as=GetAdapter();
      if (as.size()>0){
          for (Subjet s:as){

              DeleteRelationBySubjet(s.getId());
          }
      }

  }

    private void DeleteRelationBySubjet(int idsubjet){
        Call<Void> call=ServiceBA.getInstance().createService(ISDictates.class).DeleteBySubjet(idsubjet);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
