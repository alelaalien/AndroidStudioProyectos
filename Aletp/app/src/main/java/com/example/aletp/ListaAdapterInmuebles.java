package com.example.aletp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ListaAdapterInmuebles extends ArrayAdapter<Inmuebles> {
    private Context context;
    private List<Inmuebles> list;
    private LayoutInflater li;

    public ListaAdapterInmuebles(@NonNull Context context, int resource, @NonNull List<Inmuebles> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
        this.li=li;
    }
}
