package com.ale.gttt.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ale.gttt.Interfaces.ISDictates;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.entities.Dictates;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckboxsActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Subjet>  listd;
    private ArrayList<Subjet> listo;
    private ArrayList<Dictates> diclist;
    private ArrayList<Teacher> teachlist;
    private Button btnacept;
    private ListView listdic, listop;
    private ChecksAdapter adapter;
    private LinearLayout lin;
    private String a;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkboxs);
        Init();
    }

    private void Init() {
        a=SharedPreferenceManager.getInstance(getApplicationContext()).GetToken();
        id = SharedPreferenceManager.getInstance(getApplicationContext()).GetUser().getId();
        listdic=findViewById(R.id.lvsubdic);
        listop=findViewById(R.id.lvsuboption);
        btnacept=findViewById(R.id.btnaceptchecbox);



     GetSubjetsList();
     Config();

    }

    private void Config() {
        btnacept.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnacept){

            Capture();
        }

    }

    private void Capture() {

    }
    private void Adapter(ArrayList<Subjet> l, int n){
        SubjetsAdapter sa = new SubjetsAdapter(getApplicationContext(), l, 1   );

        listdic.setAdapter(sa);
        listop.setAdapter(sa);
        ArrayList<Subjet>s=new ArrayList<>();


        listd=new ArrayList<>();

            listop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Subjet sub=listo.remove(position);
                    s.add(sub);
                    sa.notifyDataSetChanged();
                    SubjetsAdapter f = new SubjetsAdapter(getApplicationContext(), s, 1 );

                    listdic.setAdapter(f);
                }
            });

    }



    private void GetSubjetsList(){

            listo = new ArrayList<>();

            Call<ArrayList<Subjet>> call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(a, id, null, 0);

            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code() == 200) {

                        try {
                            listo.addAll(response.body());
                            Adapter(listo, 0);

                        } catch (Exception e) {
                        }

                    } else if (response.code() == 404) {
                        Toast.makeText( getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d("Addfdfdfd subjet", "Recursos no encontrados");


                    } else if (response.code() == 500) {
                        Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                        Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {
                    Log.d("failure", "Hubo un error en el servidor. Reintentar");

                }
            });



    }

    private void GetTeacherList(){

        teachlist = new ArrayList<>();

        Call<ArrayList<Teacher>> call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(id, null, null, null);

        call.enqueue(new Callback<ArrayList<Teacher>>() {
            @Override
            public void onResponse(Call<ArrayList<Teacher>> call, Response<ArrayList<Teacher>> response) {
                if (response.code() == 200) {

                    try {
                        teachlist.addAll(response.body());


                    } catch (Exception e) {
                    }

                } else if (response.code() == 404) {
                    Toast.makeText( getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("Addfdfdfd subjet", "Recursos no encontrados");


                } else if (response.code() == 500) {
                    Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Teacher>> call, Throwable t) {
                Log.d("failure", "Hubo un error en el servidor. Reintentar");

            }
        });



    }

    private void GetDictatesList(int idt){

        diclist = new ArrayList<>();

        Call<ArrayList<Dictates>> call = ServiceBA.getInstance().createService(ISDictates.class).GetAllByTeacher(idt);

        call.enqueue(new Callback<ArrayList<Dictates>>() {
            @Override
            public void onResponse(Call<ArrayList<Dictates>> call, Response<ArrayList<Dictates>> response) {
                if (response.code() == 200) {

                    try {
                        diclist.addAll(response.body());


                    } catch (Exception e) {
                    }

                } else if (response.code() == 404) {
                    Toast.makeText( getApplicationContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("Addfdfdfd subjet", "Recursos no encontrados");


                } else if (response.code() == 500) {
                    Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                    Toast.makeText(getApplicationContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Dictates>> call, Throwable t) {
                Log.d("failure", "Hubo un error en el servidor. Reintentar");

            }
        });



    }


}
