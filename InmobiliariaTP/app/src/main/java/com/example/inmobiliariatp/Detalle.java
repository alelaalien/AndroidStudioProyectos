package com.example.inmobiliariatp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class Detalle extends Fragment implements PropiedadFragment.OnFragmentInteractionListener {
Bundle dato;
private TextView direccion;
    private EditText ambientes;
    private EditText precio;
private TextView uso;
private CheckBox disponible;
    private TextView tipo;
    private Button volver;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
private String[] AvenidaEspaña103={"10", "Casa", "Consultorio", "$25000", "false"};
    private String[] Pringles71={"5", "Casa", "Vivienda", "$5000", "true"};
    private String[] SanMartin96={"2", "Local", "Local", "$35000", "false"};
    private String[] Rivadavia142={"3", "Departamento", "Vivienda", "$10000", "false"};
    private String[] Colon116={"8", "Casa", "Consultorio", "$35000", "true"};

    private OnFragmentInteractionListener mListener;

    public Detalle() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Detalle newInstance(String param1, String param2) {
        Detalle fragment = new Detalle();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle datosRecuperados = getArguments();
        assert datosRecuperados != null;
        String[] carArr;
        String datoD=datosRecuperados.getString("dato");
View root=inflater.inflate(R.layout.fragment_detalle, container, false);
direccion= (TextView) root.findViewById(R.id.etdomicilio);
direccion.setText(datoD);

ambientes= (EditText) root.findViewById(R.id.etambiente);
uso= (TextView) root.findViewById(R.id.tvuso2);
tipo= (TextView) root.findViewById(R.id.tvtipo2);
precio= (EditText) root.findViewById(R.id.etprecio);
disponible=(CheckBox) root.findViewById(R.id.cbdisponible);
Button button=(Button) root.findViewById(R.id.button);

        assert datoD != null;
        if (datoD.equals("AvenidaEspaña103")) {


            ambientes.setText(AvenidaEspaña103[0]);
            precio.setText(AvenidaEspaña103[3]);
            tipo.setText(AvenidaEspaña103[1]);
            uso.setText(AvenidaEspaña103[2]);
            boolean c = Boolean.parseBoolean(AvenidaEspaña103[4]);
            disponible.setChecked(c);

        }
        if (datoD.equals("Colon116")) {

            ambientes.setText(Colon116[0]);
            precio.setText(Colon116[3]);
            tipo.setText(Colon116[1]);
            uso.setText(Colon116[2]);
            boolean c= Boolean.parseBoolean(Colon116[4]);
            disponible.setChecked(c);

        }
        if (datoD.equals("Rivadavia142")) {

            ambientes.setText(Rivadavia142[0]);
            precio.setText(Rivadavia142[3]);
            tipo.setText(Rivadavia142[1]);
            uso.setText(Rivadavia142[2]);
            boolean c= Boolean.parseBoolean(Rivadavia142[4]);
            disponible.setChecked(c);

        }
        if (datoD.equals("SanMartin96")) {

            carArr = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.SanMartin96);
            ambientes.setText(SanMartin96[0]);
            precio.setText(SanMartin96[3]);
            tipo.setText(SanMartin96[1]);
            uso.setText(SanMartin96[2]);
            boolean c= Boolean.parseBoolean(SanMartin96[4]);
            disponible.setChecked(c);

        }
        if (datoD.equals("Pringles71")) {

             ambientes.setText(Pringles71[0]);
            precio.setText(Pringles71[3]);
            tipo.setText(Pringles71[1]);
            uso.setText(Pringles71[2]);
            boolean c= Boolean.parseBoolean(Pringles71[4]);
            disponible.setChecked(c);

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       boolean a;
       String[] ak;
       String domicilioS=(String) direccion.getText();

                    if (disponible.isChecked()){
                         a=true;
                    }else{
                       a=false; }



   if(domicilioS=="AvenidaEspaña103"){
       ak=AvenidaEspaña103;
   }else if(domicilioS=="Colon116"){
ak=Colon116;
   }else if(domicilioS=="Rivadavia142"){
       ak=Rivadavia142;
   }else if(domicilioS=="SanMartin96"){
       ak=SanMartin96;
   }else{
       ak=Pringles71;
   }
 ca(ak, a);


            }
        });

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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
public void ca(String[]  x, boolean b){
       String a=Boolean.toString(b);
        x[4]=a;
        Toast.makeText(getActivity(), "Editado", Toast.LENGTH_LONG).show();

}
}
