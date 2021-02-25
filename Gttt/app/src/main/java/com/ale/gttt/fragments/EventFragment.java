package com.ale.gttt.fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.ale.gttt.AddEventActivity;
import com.ale.gttt.Interfaces.ISEvent;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.EventsAdapter;
import com.ale.gttt.entities.Event;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.io.ServiceBA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int idUser;
    private MediaPlayer mp;
    private String mParam1;
    private String mParam2;
    private EventsAdapter adapter;
    private ListView listView;
    private ArrayList<Event> listEvent;
    private ArrayList<Subjet> sublist;
    private ToggleButton tb;
    private Button rh, rl, rm, rall;
    private FloatingActionButton fab;
    private Spinner spin;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View view=inflater.inflate(R.layout.fragment_event, container, false);
                fab = view.findViewById(R.id.fab_event);
                mp=MediaPlayer.create(getContext(), R.raw.add);

                 Init(view);
                 GetAll(3, null, 0, null, 2);
        return view;

    }

    private void Init(View v) {
        idUser= SharedPreferenceManager.getInstance(getContext()).GetUser().getId();
        listView=v.findViewById(R.id.lvevent);
        tb=v.findViewById(R.id.toggleButton);
        rh=v.findViewById(R.id.btnhigh);
        rm=v.findViewById(R.id.btnmedium);
        rl=v.findViewById(R.id.btnlow);
        rall=v.findViewById(R.id.btnallp);
        spin=v.findViewById(R.id.spinner2);

        Config();

    }

    private void Config() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "a ver", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i= new Intent(view.getContext(), AddEventActivity.class);
                startActivity(i); mp.start();
            }
        });
       rall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               GetAll(3, null, 0, null, 2);
           }
       });
       rh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               GetAll(0, null, 0, null, 2);
           }
       });
       rm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               GetAll(1, null, 0, null, 2);
           }
       });
       rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAll(2, null, 0, null, 2);
            }
        });

       tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   GetAll(3, null, 0, null, 1);


               }else {
                   GetAll(3, null, 0, null, 0);


               }
           }
       });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ArrayList<String> data = new ArrayList<>();

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String title, date, typeof, ide, idsub, priority, notes;
                    date = listEvent.get(position).getDate();
                    title= listEvent.get(position).getTitle();
                    typeof=String.valueOf(listEvent.get(position).getTypeOf());
                    ide=String.valueOf(listEvent.get(position).getId());
                    notes= listEvent.get(position).getNotes();
                    idsub=String.valueOf(listEvent.get(position).getIdSubjet());
                    priority=String.valueOf(listEvent.get(position).getPriority());

                data.add(ide);
                data.add(idsub);
                data.add(title);
                data.add(typeof);
                data.add(date);
                data.add(priority);
                data.add(notes);

                Intent i = new Intent(getContext(), AddEventActivity.class);
                i.putStringArrayListExtra("arrayE", data);
                startActivity(i);
            }
        });

    }

    private void ShowAll(ArrayList<Event> list, ArrayList<Subjet> sublist) {
        try {
            adapter= new EventsAdapter(getContext(), list, sublist);
            listView.setAdapter(adapter);
        }catch (Exception e){
            System.out.println(e.getMessage()+" showall catch");
        }

    }

    private void GetAll(int n, String s, int i, ArrayList<Subjet> lists, int type){

        listEvent=new ArrayList<>();
        Call<ArrayList<Event>> call;

        if (i!=0&&n==3&&lists!=null){
            call= ServiceBA.getInstance().createService(ISEvent.class).SearchSubjet(idUser, i);
        }else if (n>=3&&i==0&&type==2){
            call= ServiceBA.getInstance().createService(ISEvent.class).GetAll(idUser);
        }else if (type<2){
            call= ServiceBA.getInstance().createService(ISEvent.class).GetTypeOf(idUser, type);
        }else {
            call= ServiceBA.getInstance().createService(ISEvent.class).GetAllParam(idUser, n);
        }

        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.code()==200){
                    listEvent.addAll(response.body());
                    System.out.println(listEvent.size()+" listaevento desde getall");

                    if (i!=0&&n==3&&lists!=null){
                        ShowAll(listEvent, lists);System.out.println(listEvent.size()+" show all desde getall "+ listEvent.size()+" evento. "+lists.size()+" subs.");
                    }else{
                        GetAllS(listEvent, null, 0);
                    }
                 }
            }
            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

            }
        });

    }

    private void GetAllS(ArrayList<Event> list, String s, int val) {


            sublist = new ArrayList<>();
            int id = SharedPreferenceManager.getInstance(getContext()).GetUser().getId();

            Call<ArrayList<Subjet>> call= ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, null, 0);
            if (s == null&&val==0) {
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, null, 0);
            }else if (list==null&& val!=0&&s!=null){
            call = ServiceBA.getInstance().createService(ISSubjet.class).GetById(val);
        }else if (s!=null&&val==0&&list==null){
                call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, s, 0);
            }

            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code() == 200) {

                        sublist.addAll(response.body());

                        if (val==0&&s==null){
                            ShowAll(list, sublist);
                            try {
                                SpinnConf(sublist);
                            }catch(Exception e){
                                Toast.makeText(getContext(), "No se accedio a las materias: error "+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }else if(s!=null&&val==0&&list==null){
                            GetAll(3, null, sublist.get(0).getId(), sublist, 2);
                        }

                    } else if (response.code() == 404) {
                        Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
                        Log.d("Addfdfdfd subjet", "Recursos no encontrados");


                    } else if (response.code() == 500) {
                        Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");

                        Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Subjet>> call, Throwable t) {
                    Log.d("failure", "Hubo un error en el servidor. Reintentar");

                }
            });

    }

    private void SpinnConf(ArrayList<Subjet> sublist) {

        ArrayList<String> array= new ArrayList<>();
        array.add("Seleccionar");
        for (int i=0; i<sublist.size(); i++){
            int n=i+1;
            array.add(sublist.get(i).getName());
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), R.layout.spinner_item, array);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spin.getSelectedItemPosition()!=0){
                    GetAllS(null, spin.getSelectedItem().toString(), 0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public EventFragment() {     }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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
