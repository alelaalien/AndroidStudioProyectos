package com.example.aletp.ui.Pagos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aletp.Contratos;
import com.example.aletp.ListaAdapterContratos;
import com.example.aletp.ListaAdapterPagos;
import com.example.aletp.Pagos;
import com.example.aletp.R;

import java.util.ArrayList;
import java.util.Objects;

public class PagosFragment extends Fragment {
    ArrayList<Pagos> pagosrrayList= new ArrayList<>();
    private PagosViewModel toolsViewModel;
    private Button btn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(PagosViewModel.class);
      final   View root = inflater.inflate(R.layout.fragment_pagos, container, false);
        Spinner spinner=root.findViewById(R.id.spinnerPagos);
        btn=root.findViewById(R.id.button4);
        int i= spinner.getSelectedItemPosition();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejemplo(root);
                Limpiar(root);

                CargarDatos(ejemplo(root));
                GenerarListView(root);
            }
        });
        return root;
    }
    public int ejemplo(View view){
        Spinner spinner = view.findViewById(R.id.spinnerPagos);
        int i= spinner.getSelectedItemPosition();
        return i;
    }
    public void GenerarListView(View view){
        ArrayAdapter<Pagos> adapter=new ListaAdapterPagos(Objects.requireNonNull(getActivity()), R.layout.pagositem, pagosrrayList,getLayoutInflater());
        ListView listView=view.findViewById(R.id.listaPagos);
        listView.setAdapter(adapter);
    }

    public void Limpiar(View view){

pagosrrayList.clear();


    }

    public void  CargarDatos(int n){
        if (n==0){
            pagosrrayList.add(new Pagos("20/03/2019", 14000, 13));
            pagosrrayList.add(new Pagos("20/02/2019", 14000, 12));
            pagosrrayList.add(new Pagos("20/01/2019", 13000, 11));
            pagosrrayList.add(new Pagos("20/12/2018", 13000, 10));
        }
        if (n==1){
            pagosrrayList.add(new Pagos("31/03/2018", 200000, 6));
            pagosrrayList.add(new Pagos("31/02/2018", 200000, 5));
            pagosrrayList.add(new Pagos("31/01/2018", 150000, 4));
            pagosrrayList.add(new Pagos("31/12/2017", 100000, 3));
        }
        if (n==2){
            pagosrrayList.add(new Pagos("02/05/2019", 29990, 5));
            pagosrrayList.add(new Pagos("02/06/2019", 30990, 6));
            pagosrrayList.add(new Pagos("02/07/2019", 30990, 7));
            pagosrrayList.add(new Pagos("02/08/2019", 38990, 8));
        }
    }
}