package com.example.aletp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListaAdapterContratos extends ArrayAdapter<Contratos> {
    private Context context;
    private List<Contratos> list;
    private LayoutInflater li;

    public ListaAdapterContratos(@NonNull Context context, int resource, @NonNull List<Contratos> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
        this.li=li;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= convertView;
        if (view==null){
            view=li.inflate(R.layout.contratositem, parent, false);

        }
        Contratos contratos=list.get(position);
        TextView inicio=view.findViewById(R.id.tvinicio);
        TextView fin=view.findViewById(R.id.tvfin);
        TextView precio=view.findViewById(R.id.tvprecio);
        inicio.setText(contratos.getInicio());
        fin.setText(contratos.getFin());
        precio.setText(contratos.getMonto()+"");

        return view;

}}
