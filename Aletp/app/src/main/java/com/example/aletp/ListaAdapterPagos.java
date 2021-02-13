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

public class ListaAdapterPagos extends ArrayAdapter<Pagos> {
    private Context context;
    private List<Pagos> list;
    private LayoutInflater li;

    public ListaAdapterPagos(@NonNull Context context, int resource, @NonNull List<Pagos> objects, LayoutInflater li) {
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
            view=li.inflate(R.layout.pagositem, parent, false);

        }
        Pagos pagos=list.get(position);
        TextView fecha=view.findViewById(R.id.tvfecha);
        TextView importe=view.findViewById(R.id.tvImporte);
        TextView nro=view.findViewById(R.id.tvnumero);
        fecha.setText(pagos.getFecha());
        importe.setText(pagos.getImporte()+"");
        nro.setText(pagos.getNroPago()+"");

        return view;}

}
