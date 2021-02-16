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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ale.gttt.AddSubjetActivity;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
import com.ale.gttt.Tools.SubjetsAdapter;
import com.ale.gttt.entities.Subjet;
import com.ale.gttt.io.ServiceBA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubjetFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Subjet> viewAll;
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fab;
    private MediaPlayer mp;
    private ListView listView;
    private SubjetsAdapter adapter;
    private EditText ettxtmateria;

    private OnFragmentInteractionListener mListener;

    public SubjetFragment() {
    }

    public static SubjetFragment newInstance(String param1, String param2) {
        SubjetFragment fragment = new SubjetFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjet, container, false);
        Init(view);
        GetAll(null);
        Config();
        return view;
    }

    private void Init(View view) {
        fab = view.findViewById(R.id.fab_subjet);
        mp = MediaPlayer.create(getContext(), R.raw.add);
        listView = view.findViewById(R.id.rvmateriasrv);
        ettxtmateria=view.findViewById(R.id.ettxtmateria);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void Config() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "a ver", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(view.getContext(), AddSubjetActivity.class);
                startActivity(i);
                mp.start();
            }
        });













        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ArrayList<String> data = new ArrayList<>();

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name, id_sub, hour;
                name = viewAll.get(position).getName();

                id_sub = String.valueOf(viewAll.get(position).getId());
                hour = viewAll.get(position).getClass_();
                data.add(name);
                data.add(id_sub);
                if (hour!=null){
                    data.add(hour);
                }else{
                    data.add("");
                }

                data.add(hour);

                Intent i = new Intent(getContext(), AddSubjetActivity.class);
                i.putStringArrayListExtra("arraydata", data);
             //   i.putParcelableArrayListExtra(data, )
                startActivity(i);

            }
        });

        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GetAll(ettxtmateria.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ettxtmateria.getText().toString().equals("")){
             //       GetAll(null);
                }
            }
        };

        ettxtmateria.addTextChangedListener(textWatcher);

       // GetAll(null);


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
        void onFragmentInteraction(Uri uri);
    }

    private void GetAll(String s) {

        if (s==null){
            viewAll = new ArrayList<>();
            int id = SharedPreferenceManager.getInstance(getContext()).GetUser().getId();

            Call<ArrayList<Subjet>> call;

            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, null, 0);

            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code() == 200) {

                        try {
                            viewAll.addAll(response.body());
                            ShowAll(viewAll, 0);

                        } catch (Exception e) {
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

        }else {
            viewAll = new ArrayList<>();
            int id = SharedPreferenceManager.getInstance(getContext()).GetUser().getId();

            Call<ArrayList<Subjet>> call;

            call = ServiceBA.getInstance().createService(ISSubjet.class).GetAll(id, s, 0);

            call.enqueue(new Callback<ArrayList<Subjet>>() {
                @Override
                public void onResponse(Call<ArrayList<Subjet>> call, Response<ArrayList<Subjet>> response) {
                    if (response.code() == 200) {

                        try {
                            viewAll.addAll(response.body());
                            ShowAll(viewAll, 0);

                        } catch (Exception e) {
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



    }

    private void ShowAll(ArrayList<Subjet> list, int i) {
        adapter= new SubjetsAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }





}

