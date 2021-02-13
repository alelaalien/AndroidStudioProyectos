package com.example.aletp.ui.Inquilinos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aletp.Inquilinos;
import com.example.aletp.InquilinosFragment1;
import com.example.aletp.ListaAdapterInquilinos;
import com.example.aletp.R;

import java.util.ArrayList;
import java.util.Objects;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel inquilinosViewModel;

    ArrayList<Inquilinos> inquilinosArrayList= new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InquilinosFragment1.OnFragmentInteractionListener mListener;

    public InquilinosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InquilinosFragment1 newInstance(String param1, String param2) {
        InquilinosFragment1 fragment = new InquilinosFragment1();
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
        View root= inflater.inflate(R.layout.fragment_inquilinos, container, false);
        cargarDatos();
        GenerarListView(root);

        return root;
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
        if (context instanceof InquilinosFragment1.OnFragmentInteractionListener) {
            mListener = (InquilinosFragment1.OnFragmentInteractionListener) context;
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
    public void cargarDatos(){
        inquilinosArrayList.add(new Inquilinos("Marta", "Sanchez", "B° Los Paraisos 123",  3333333 , 455653334));
        inquilinosArrayList.add(new Inquilinos("Carla", "Nuñez", "B° Los Colibries 123",  444444 , 453676334));
        inquilinosArrayList.add(new Inquilinos("Gabriela", "Lucero", "B° Terrazas 123",  55555 , 453345334));
        inquilinosArrayList.add(new Inquilinos("Mirta", "Nolegrand", "B° Telepostal 123",  666666 , 453435334));
        inquilinosArrayList.add(new Inquilinos("Betty", "Binzon", "B° Fuerza Aerea 123",  77777 , 4534534));


    }
    public void GenerarListView(View view){
        ArrayAdapter<Inquilinos> adapter=new ListaAdapterInquilinos(Objects.requireNonNull(getActivity()), R.layout.inteminquilinos, inquilinosArrayList,getLayoutInflater());
        ListView listView=view.findViewById(R.id.listaInquilinos);
        listView.setAdapter(adapter);
    }
}