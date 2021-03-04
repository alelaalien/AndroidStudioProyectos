package com.ale.gttt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISType;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.Checks;
import com.ale.gttt.Tools.EventsAdapter;
import com.ale.gttt.Tools.TypeAdapter;
import com.ale.gttt.Tools.Utilities;
import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.TypeOf;
import com.ale.gttt.io.ServiceBA;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddType extends AppCompatActivity  implements View.OnClickListener {

    private Button btnedit, btndelete, btnsave , btnbacknew ;
    private TextView etname, etdesc, tvidto;
    private ListView lv;
    private ArrayList<TypeOf> viewAll;
    private TypeAdapter adapter;
    private Utilities u;
    private int idU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        Init();
        Config();
    }

    private void Init() {
        btnbacknew=findViewById(R.id.btnbacknewtype);
        btndelete=findViewById(R.id.btndeletetype);
        btnsave=findViewById(R.id.btnsavetype);
        btnedit=findViewById(R.id.btnedittype);
        etname=findViewById(R.id.etnametype);
        tvidto=findViewById(R.id.tvidto);
        etdesc=findViewById(R.id.etdesctypeedit);
        etdesc.setSelectAllOnFocus(true);
        viewAll=new ArrayList<>();
        u=new Utilities();
        lv=findViewById(R.id.lv_type);
        idU= SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        btnedit.setEnabled(false);
        btnedit.setEnabled(false);
        etdesc.setEnabled(false);

        GetAll( );
        Config();
        ShowAll(viewAll);
    }

    private void ShowAll(ArrayList<TypeOf> list) {

            adapter= new TypeAdapter(getApplicationContext(), list);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }

    private void GetAll( ) {
        viewAll = new ArrayList<>();
        Call<ArrayList<TypeOf>> call;

            call = ServiceBA.getInstance().createService(ISType.class).GetAll(idU);

        call.enqueue(new Callback<ArrayList<TypeOf>>() {
            @Override
            public void onResponse(Call<ArrayList<TypeOf>> call, Response<ArrayList<TypeOf>> response) {
                if (response.code() == 200) {

                    viewAll.addAll(response.body());
                    ShowAll(viewAll);

                }  else    if (response.code()==404){
                    Log.d("error",  "404");

                }   else   if (response.code()==500){
                    Log.d("error",  "500");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<TypeOf>> call, Throwable t) {
                Log.d("error",  t.getMessage()+"");

            }
        });

    }

    private void Config() {

        btnbacknew.setOnClickListener(this);
        btnedit.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String desc= viewAll.get(position).getDescription();
               int idd=viewAll.get(position).getId();
               etdesc.setText(desc);
                tvidto.setText(String.valueOf(idd));
                btndelete.setEnabled(true);                btnedit.setEnabled(true);


            }
        });
    }


    @Override
    public void onClick(View v) {

        if (v==btnbacknew){
            Intent i=new Intent(getApplicationContext(), Checks.class);startActivity(i);}

        if (v==btndelete){


            GetEvents();
            etdesc.setText("");
            btnedit.setEnabled(false);
         btndelete.setEnabled(false);
            etdesc.setEnabled(false);
          GetAll( );
        }
        if (v==btnedit){

            String title=btnedit.getText().toString();
            if (title.equals("Editar")){
                String sa="Aplicar";
                etdesc.setEnabled(true);
                btnedit.setText(sa);
            }
            if (title.equals("Aplicar")){
                Edit();
                String editar="Editar";
                etdesc.setText("");
                btnedit.setEnabled(false);
                btndelete.setEnabled(false);
                etdesc.setEnabled(false);
                btnedit.setText(editar);
            }
        }
        if (v==btnsave){

            AddType();
        }

    }

    private void Edit() {

        TypeOf t=new TypeOf();
        String desc=etdesc.getText().toString();
        t.setIdUser(idU);
        String id=tvidto.getText().toString();
        int e=Integer.parseInt(id);
        t.setId(e);
        t.setDescription(desc);
        Call<Void> call= ServiceBA.getInstance().createService(ISType.class).Update(t.getId(), t);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    GetAll( );
                    Toast.makeText(getApplicationContext(), "Hecho", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d("error",  t.getMessage()+"");
            }
        });
    }

    private void DeleteType() {
        String id=tvidto.getText().toString();
        int idt=Integer.parseInt(id);
        Call<Void> call= ServiceBA.getInstance().createService(ISType.class).Delete(idt);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    GetAll( );
                    Toast.makeText(getApplicationContext(), "Hecho", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d("error",  t.getMessage()+"");
            }
        });
    }

    private void AddType(){
        TypeOf t= new TypeOf();
        String desc=etname.getText().toString();
        t.setDescription(desc);
        t.setIdUser(idU);
        Call<Void> call= ServiceBA.getInstance().createService(ISType.class).Create(t);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){

                    etname.setText("");
                    GetAll( );
                    Toast.makeText(getApplicationContext(), "Hecho", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d("error",  t.getMessage()+"");
            }
        });

    }

    private void GetEvents(){
        ArrayList<Event>event=new ArrayList<>();
        int type=Integer.parseInt(tvidto.getText().toString());
        Call<ArrayList<Event>> call= ServiceBA.getInstance().createService(ISEvent.class).GetTypeOf(idU,type );
        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.code()==200){
                    if (response.body().size()>0){
                        event.addAll(response.body());
                        for (Event e:event){
                            DeleteEvents(e.getId());
                        }
                    }else{
                        DeleteType();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

            }
        });
    }

    private void DeleteEvents(int id) {
        Call<Void> call=ServiceBA.getInstance().createService(ISEvent.class).Delete(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200){
                    DeleteType();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void Alert(){

        AlertDialog.Builder dialog= new AlertDialog.Builder(AddType.this);
        dialog.setMessage("¿Eliminar?").setCancelable(true)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        try {
                           GetEvents();


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


}
