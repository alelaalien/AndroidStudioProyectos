package com.ale.gttt.fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ale.gttt.AddTeacherActivity;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.TeachersAdapter;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.entities.Teacher;
import com.ale.gttt.io.ServiceBA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherFragment extends Fragment {

    private EditText etserchname;
    private RadioButton rdname, rdsur, rdnick, rdall;
    private RadioGroup rgt;
    private TeachersAdapter adapter;
    private ArrayList<Teacher> viewAll;
    private ArrayList<String> sublistString;
    private ArrayList<Subjet> sublist;
    private ArrayList<Integer> ids;
    private ListView listView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private MediaPlayer mp;
    private int idUser;
    private OnFragmentInteractionListener mListener;
    private  FloatingActionButton fab;

    public TeacherFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher, container, false);
        fab = view.findViewById(R.id.fab_teacher);
        mp= MediaPlayer.create(getContext(), R.raw.add);
            Init(view);
        GetAll(null, 0, 1);
        GetSubjets(null);
            Config(view);


        return view;
    }

    private void Init(View view) {
         idUser= SharedPreferenceManager.getInstance(getContext()).GetUser().getId();
        etserchname=view.findViewById(R.id.etbuscardocentename);
        listView=view.findViewById(R.id.lvteachers);
        mp = MediaPlayer.create(getContext(), R.raw.add);
        rdname=view.findViewById(R.id.rdname);
        rdnick=view.findViewById(R.id.rdnick);
        rdsur=view.findViewById(R.id.rdsur);
        rgt=view.findViewById(R.id.rgt);
        rdall=view.findViewById(R.id.rball);
        sublistString=new ArrayList<String>();
    }

    private void Config(View view) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "a ver", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i= new Intent(view.getContext(), AddTeacherActivity.class);
                startActivity(i);
                mp.start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> data = new ArrayList<>();
                String name, idt, cel, email, sub, surname, nick;

                name = viewAll.get(position).getName();
                idt = String.valueOf(viewAll.get(position).getId());
                cel= String.valueOf(viewAll.get(position).getCelphone());
                email = viewAll.get(position).getEmail();
                sub= String.valueOf(viewAll.get(position).getSubjet());
                surname= viewAll.get(position).getSurname();
                nick= viewAll.get(position).getNick();



                data.add(idt);//0
                data.add(name);//1
                data.add(surname);//2
                data.add(nick);//3
                data.add(sub);//4
                data.add(cel);//5
                data.add(email);//6
                Intent i = new Intent(getContext(), AddTeacherActivity.class);
                i.putStringArrayListExtra("datat", data);

                startActivity(i);
            }
        });

        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


 }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etserchname.addTextChangedListener(textWatcher);


        rgt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Parameters(checkedId);
            }
        });

    }

    private void Parameters(int checkedId) {
        String parameter= etserchname.getText().toString();
        if (parameter.length()>0){
            switch (checkedId){
                case R.id.rdname:
                    GetAll(parameter, 1, 1);
                    Log.d("checked", "name");
                    break;
                case R.id.rdsur:
                    GetAll(parameter, 2, 1);
                    Log.d("checked", "sur");

                    break;
                case R.id.rdnick:
                    GetAll(parameter, 3, 1);
                    Log.d("checked", "nick");

                    break;
                case R.id.rball:
                    GetAll(parameter, 0, 1);
                    etserchname.setText("");
                    Log.d("checked", "all");

                    break;
                case R.id.rbsub:
                    GetSubjets(parameter);


                    break;
            }
        }
    }


    private void ShowAll(ArrayList<Teacher> list) {
        adapter= new TeachersAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }
    private void Spinner(ArrayList<Subjet> list){

        for(int i = 0; i<  list.size(); i++){
            sublistString.add(list.get(i).getName());
            Log.d("materia"+list.get(i).getId(), ""+list.get(i).getName());
        }

    }

    private void GetSubjets(String name){
        sublist = new ArrayList<>();
        Call<ArrayList<Subjet>> call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, null, 0);

        if (name!=null){
            Log.d("materia", name);

            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(idUser, name, 0);
 }
        call.enqueue(new Callback<ArrayList<Subjet>>() {
            @Override
            public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {

                if (response.code() == 200) {
                    sublist.addAll(response.body());
                    if (name==null){


                        Spinner(sublist);
                        Log.d("response", "null");


                    }else{
                        if (response.body().size()>0){
                            int val=sublist.get(0).getId();
                            GetAll(null, 4, val);
                            Log.d("materia", val+""+response.body().size());
                        }


                    }


                } else if (response.code() == 404) {
                    Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                    Log.d("teacher materias", "Recursos no encontrados");


                } else if (response.code() == 500) {
                    Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                    Toast.makeText(getContext(), "Hubo un error en el servidor. Error 500", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {

            }
        });


    }




    private void GetAll(String s, int i, int subjet) {

            viewAll = new ArrayList<>();
            Call<ArrayList<Teacher>> call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(idUser, null, null,null);

            switch (i){
                case 0:

                    call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(idUser, null, null,null);
                    break;
                case 1:

                    call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(idUser, s, null, null);
                    break;
                case 2:

                    call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(idUser, null, s, null);
                    break;
                case 3:

                    call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(idUser, null, null, s);
                    break;
                case 4:

                    call = ServiceBA.getInstance().createService(ISTeacher.class).GetAllFilters(idUser, subjet);
                    break;

            }

            call.enqueue(new Callback<ArrayList<Teacher>>() {
                @Override
                public void onResponse(Call<ArrayList<Teacher>> call, Response<ArrayList<Teacher>> response) {
                    if (response.code() == 200) {

                            viewAll.addAll(response.body());
                            ShowAll(viewAll);
                    } else if (response.code() == 404) {
                        Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d("teacher", "Recursos no encontrados");


                    } else if (response.code() == 500) {
                        Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                        Toast.makeText(getContext(), "Hubo un error en el servidor. Error 500", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Teacher>> call, Throwable t) {
                    Log.d("failure", "Hubo un error en el servidor. Reintentar");
                }
            });
        }




    public static TeacherFragment newInstance(String param1, String param2) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
