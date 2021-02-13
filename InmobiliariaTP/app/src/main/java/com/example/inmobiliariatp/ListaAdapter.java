package com.example.inmobiliariatp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListaAdapter extends ArrayAdapter<Contrato>  {
private Context context;
private List<Contrato> list;
private  LayoutInflater li;

    public ListaAdapter(@NonNull Context context, int resource, @NonNull List<Contrato> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
        this.li=li;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View itemView=convertView;
       if(itemView==null){
        itemView=li.inflate(R.layout.item_contratos, parent,false);
       }
       Contrato contrato=list.get(position);
        TextView inicio= itemView.findViewById(R.id.tvinicio);
inicio.setText(contrato.getInicio());
TextView fin=itemView.findViewById(R.id.tvfin);
fin.setText(contrato.getFin());
TextView monto=itemView.findViewById(R.id.tvmonto);
String cantidad= contrato.getMonto();

monto.setText(cantidad);


        return itemView;
    }
}
