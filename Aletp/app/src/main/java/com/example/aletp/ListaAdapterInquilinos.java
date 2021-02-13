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

public class ListaAdapterInquilinos extends ArrayAdapter<Inquilinos> {
    private Context context;
    private List<Inquilinos> list;
    private LayoutInflater li;

    public ListaAdapterInquilinos(@NonNull Context context, int resource, @NonNull List<Inquilinos> objects, LayoutInflater li) {
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
      view=li.inflate(R.layout.inteminquilinos, parent, false);

  }
  Inquilinos inquilinos=list.get(position);
        TextView nombre=view.findViewById(R.id.tvnombre);
        TextView apellido=view.findViewById(R.id.tvapellido);
        TextView dni=view.findViewById(R.id.tvdni);
        TextView direccion=view.findViewById(R.id.tvdomicilio);
        TextView telefono=view.findViewById(R.id.tvtelefono);

nombre.setText(inquilinos.getNombre());
apellido.setText(inquilinos.getApellido());
direccion.setText(inquilinos.getDireccion());
String tel=Integer.toString(inquilinos.getTelefono());
telefono.setText(tel);
String doc=Integer.toString(inquilinos.getDni());
dni.setText(doc);
return view;

    }
}
