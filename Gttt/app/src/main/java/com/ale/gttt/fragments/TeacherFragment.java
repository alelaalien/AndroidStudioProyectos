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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ale.gttt.AddTeacherActivity;
import com.ale.gttt.Interfaces.ISSubjet;
import com.ale.gttt.Interfaces.ISTeacher;
import com.ale.gttt.R;
import com.ale.gttt.Session.SharedPreferenceManager;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    MediaPlayer mp;
    private OnFragmentInteractionListener mListener;

    public TeacherFragment() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_teacher, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_teacher);
        mp= MediaPlayer.create(getContext(), R.raw.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "a ver", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i= new Intent(view.getContext(), AddTeacherActivity.class);
                startActivity(i); mp.start();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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


//    private void GetAll() {
//        viewAll = new ArrayList<>();
//        int id = SharedPreferenceManager.getInstance(getContext()).GetUser().getId();
//
//        Call<ArrayList<Teacher>> call;
//
//        call = ServiceBA.getInstance().createService(ISTeacher.class).GetAll(id);
//
//        call.enqueue(new Callback<ArrayList<Teacher>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Teacher>> call, Response<ArrayList<Teacher>> response) {
//                if (response.code() == 200) {
//
//                    try {
//                        viewAll.addAll(response.body());
//                        ShowAll(viewAll, 0);
//
//                    } catch (Exception e) {
//                    }
//
//                } else if (response.code() == 404) {
//                    Toast.makeText(getContext(), "Recursos no encontrados", Toast.LENGTH_LONG).show();
//                    Log.d("teacher", "Recursos no encontrados");
//
//
//                } else if (response.code() == 500) {
//                    Log.d("Adddfdfdf subjet", "Hubo un error en el servidor. Reintentar");
//
//                    Toast.makeText(getContext(), "Hubo un error en el servidor. Reintentar", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Teacher>> call, Throwable t) {
//                Log.d("failure", "Hubo un error en el servidor. Reintentar");
//
//            }
//        });
//
//    }



}
